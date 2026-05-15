package com.linguaceleris.auth.impl.ui.signin

import androidx.credentials.exceptions.GetCredentialCancellationException
import app.cash.turbine.test
import com.linguaceleris.auth.impl.navigation.navigateToEmailSignIn
import com.linguaceleris.auth.impl.navigation.navigateToRegistration
import com.linguaceleris.auth.impl.ui.model.SignInWithAnonymouslyError
import com.linguaceleris.auth.impl.ui.model.SignInWithGoogleError
import com.linguaceleris.auth.impl.ui.signin.SignInMocks.getWebClientIdUseCase
import com.linguaceleris.auth.impl.ui.signin.SignInMocks.navigator
import com.linguaceleris.auth.impl.ui.signin.SignInMocks.signInAnonymouslyUseCase
import com.linguaceleris.auth.impl.ui.signin.SignInMocks.signInWithGoogleUseCase
import com.linguaceleris.home.api.startWithHome
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class SignInViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: SignInViewModel

        beforeSpec { Dispatchers.setMain(testDispatcher) }
        afterSpec { Dispatchers.resetMain() }

        beforeEach {
            clearAllMocks()
            viewModel = SignInViewModel(
                navigator = navigator,
                signInWithGoogleUseCase = signInWithGoogleUseCase,
                getWebClientIdUseCase = getWebClientIdUseCase,
                signInAnonymouslyUseCase = signInAnonymouslyUseCase,
            )
        }

        Given("SignInViewModel") {

            When("OnEmailSignInClick event is received") {
                Then("it should navigate to EmailSignInScreen") {
                    viewModel.onEvent(SignInEvent.OnEmailSignInClick)
                    verify { navigator.navigateToEmailSignIn() }
                }
            }

            When("OnRegistrationClick event is received") {
                Then("it should navigate to RegistrationScreen") {
                    viewModel.onEvent(SignInEvent.OnRegistrationClick)
                    verify { navigator.navigateToRegistration() }
                }
            }

            When("OnSignInAsGuestClick event is received") {
                Then("it should show anonymous sign in dialog") {
                    viewModel.state.value.showAnonymousSignInDialog shouldBe false
                    viewModel.onEvent(SignInEvent.OnSignInAsGuestClick)
                    viewModel.state.value.showAnonymousSignInDialog shouldBe true
                }
            }

            When("OnAnonymousSignInCancelClick event is received") {
                Then("it should hide anonymous sign in dialog") {
                    viewModel.onEvent(SignInEvent.OnSignInAsGuestClick)
                    viewModel.state.value.showAnonymousSignInDialog shouldBe true
                    viewModel.onEvent(SignInEvent.OnAnonymousSignInCancelClick)
                    viewModel.state.value.showAnonymousSignInDialog shouldBe false
                }
            }

            When("OnGoogleSignInClick event is received") {
                Then("it should show loading and send SignInWithGoogle effect") {
                    val webClientId = "test_client_id"

                    every { getWebClientIdUseCase() } returns webClientId

                    viewModel.effect.test {
                        viewModel.onEvent(SignInEvent.OnGoogleSignInClick)
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.isLoading shouldBe true
                        awaitItem() shouldBe SignInEffect.SignInWithGoogle(webClientId)
                    }
                }
            }

            When("OnAnonymousSignInConfirmClick is called and succeeds") {
                Then("it should hide dialog, show loading, and navigate to Home") {
                    coEvery { signInAnonymouslyUseCase() } just Runs

                    viewModel.onEvent(SignInEvent.OnAnonymousSignInConfirmClick)
                    viewModel.state.value.showAnonymousSignInDialog shouldBe false
                    viewModel.state.value.isLoading shouldBe true
                    testDispatcher.scheduler.advanceUntilIdle()
                    coVerify { signInAnonymouslyUseCase() }
                    verify { navigator.startWithHome() }
                }
            }

            When("OnAnonymousSignInConfirmClick is called and fails") {
                Then("it should hide dialog, hide loading, and show error snackbar") {
                    coEvery { signInAnonymouslyUseCase() } throws Exception("Auth failed")

                    viewModel.effect.test {
                        viewModel.onEvent(SignInEvent.OnAnonymousSignInConfirmClick)
                        viewModel.state.value.showAnonymousSignInDialog shouldBe false
                        viewModel.state.value.isLoading shouldBe true
                        testDispatcher.scheduler.advanceUntilIdle()
                        coVerify { signInAnonymouslyUseCase() }
                        viewModel.state.value.isLoading shouldBe false
                        awaitItem() shouldBe SignInEffect.ShowSnackBarError(SignInWithAnonymouslyError)
                    }
                }
            }

            When("OnGoogleTokenReceived is called and succeeds") {
                val token = "google_token"
                coEvery { signInWithGoogleUseCase(token) } returns true

                Then("it should show loading and navigate to Home") {
                    viewModel.onEvent(SignInEvent.OnGoogleTokenReceived(token))
                    testDispatcher.scheduler.advanceUntilIdle()
                    viewModel.state.value.isLoading shouldBe true
                    coVerify { signInWithGoogleUseCase(token) }
                    verify { navigator.startWithHome() }
                }
            }

            When("OnGoogleTokenReceived fails") {
                val token = "google_token"

                Then("it should hide loading and show error snackbar") {
                    coEvery { signInWithGoogleUseCase(token) } throws Exception("Auth failed")

                    viewModel.effect.test {
                        viewModel.onEvent(SignInEvent.OnGoogleTokenReceived(token))
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.isLoading shouldBe false
                        awaitItem() shouldBe SignInEffect.ShowSnackBarError(SignInWithGoogleError)
                    }
                }
            }

            When("OnGoogleGetCredentialException is received with cancellation") {
                Then("it should just hide loading") {
                    val exception = mockk<GetCredentialCancellationException>()

                    viewModel.onEvent(SignInEvent.OnGoogleGetCredentialException(exception))
                    viewModel.state.value.isLoading shouldBe false
                }
            }

            When("OnGoogleGetCredentialException is received with other exception") {
                Then("it should hide loading and show error snackbar") {
                    val exception = Exception("Other error")

                    viewModel.effect.test {
                        viewModel.onEvent(SignInEvent.OnGoogleGetCredentialException(exception))
                        viewModel.state.value.isLoading shouldBe false
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe SignInEffect.ShowSnackBarError(SignInWithGoogleError)
                    }
                }
            }
        }
    },
)
