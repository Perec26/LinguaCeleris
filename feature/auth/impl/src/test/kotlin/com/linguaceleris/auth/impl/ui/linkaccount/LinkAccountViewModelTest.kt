package com.linguaceleris.auth.impl.ui.linkaccount

import androidx.credentials.exceptions.GetCredentialCancellationException
import app.cash.turbine.test
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.linguaceleris.auth.impl.ui.linkaccount.LinkAccountMocks.getWebClientIdUseCase
import com.linguaceleris.auth.impl.ui.linkaccount.LinkAccountMocks.linkWithGoogleUseCase
import com.linguaceleris.auth.impl.ui.linkaccount.LinkAccountMocks.navigator
import com.linguaceleris.auth.impl.ui.model.SignInWithGoogleError
import com.linguaceleris.auth.impl.ui.model.SignInWithGoogleUserCollisionError
import com.linguaceleris.home.api.backToHomeWithResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class LinkAccountViewModelTest : BehaviorSpec(
    {
        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: LinkAccountViewModel

        fun createViewModel() = LinkAccountViewModel(
            navigator = navigator,
            getWebClientIdUseCase = getWebClientIdUseCase,
            linkWithGoogleUseCase = linkWithGoogleUseCase,
        )

        beforeSpec {
            Dispatchers.setMain(testDispatcher)
        }

        afterSpec {
            Dispatchers.resetMain()
            unmockkAll()
        }

        beforeEach {
            clearAllMocks()
            viewModel = createViewModel()
        }

        Given("LinkAccountViewModel") {
            When("OnBackClicked event received") {
                Then("it should navigate back") {
                    viewModel.onEvent(LinkAccountEvent.OnBackClicked)
                    verify { navigator.back() }
                }
            }

            When("OnGoogleSignInClick event received") {
                Then("it should show loading and send SignInWithGoogle effect") {
                    val webClientId = "test_client_id"
                    every { getWebClientIdUseCase() } returns webClientId

                    viewModel.effect.test {
                        viewModel.onEvent(LinkAccountEvent.OnGoogleSignInClick)

                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.isLoading shouldBe true
                        awaitItem() shouldBe LinkAccountEffect.SignInWithGoogle(webClientId)
                    }
                }
            }

            When("OnGoogleGetCredentialException event received") {
                And("exception is GetCredentialCancellationException") {
                    Then("it should only hide loading") {
                        val exception = GetCredentialCancellationException()

                        viewModel.onEvent(LinkAccountEvent.OnGoogleSignInClick)
                        viewModel.onEvent(LinkAccountEvent.OnGoogleGetCredentialException(exception))
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.isLoading shouldBe false
                    }
                }

                And("exception is other Exception") {
                    Then("it should hide loading and show error snackbar") {
                        val exception = Exception("Random error")

                        viewModel.effect.test {
                            viewModel.onEvent(LinkAccountEvent.OnGoogleSignInClick)
                            viewModel.onEvent(LinkAccountEvent.OnGoogleGetCredentialException(exception))
                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.isLoading shouldBe false
                            expectMostRecentItem() shouldBe LinkAccountEffect.ShowSnackBarError(SignInWithGoogleError)
                        }
                    }
                }
            }

            When("OnGoogleTokenReceived event received") {
                val token = "google_token"

                And("linking succeeds") {
                    Then("it should show loading and navigate to home") {
                        coEvery { linkWithGoogleUseCase(token) } returns true

                        viewModel.onEvent(LinkAccountEvent.OnGoogleTokenReceived(token))
                        viewModel.state.value.isLoading shouldBe true

                        testDispatcher.scheduler.advanceUntilIdle()

                        coVerify { linkWithGoogleUseCase(token) }
                        coVerify { navigator.backToHomeWithResult() }
                    }
                }

                And("linking fails with FirebaseAuthUserCollisionException") {
                    Then("it should hide loading and show collision error") {
                        val exception = mockk<FirebaseAuthUserCollisionException>(relaxed = true)
                        coEvery { linkWithGoogleUseCase(token) } throws exception

                        viewModel.effect.test {
                            viewModel.onEvent(LinkAccountEvent.OnGoogleTokenReceived(token))
                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.isLoading shouldBe false
                            awaitItem() shouldBe LinkAccountEffect.ShowSnackBarError(SignInWithGoogleUserCollisionError)
                        }
                    }
                }

                And("linking fails with other exception") {
                    Then("it should hide loading and show generic error") {
                        val exception = Exception("Generic error")
                        coEvery { linkWithGoogleUseCase(token) } throws exception

                        viewModel.effect.test {
                            viewModel.onEvent(LinkAccountEvent.OnGoogleTokenReceived(token))
                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.isLoading shouldBe false
                            awaitItem() shouldBe LinkAccountEffect.ShowSnackBarError(SignInWithGoogleError)
                        }
                    }
                }
            }
        }
    },
)
