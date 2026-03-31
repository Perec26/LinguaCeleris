package com.linguaceleris.auth.impl.ui.emailverification

import app.cash.turbine.test
import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.auth.impl.ui.VerificationSnackbarError
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationMocks.getEmailVerificationUseCase
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationMocks.getSendAgainTimerUseCase
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationMocks.navigator
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationMocks.sendEmailVerificationUseCase
import com.linguaceleris.auth.impl.ui.emailverification.EmailVerificationMocks.signOutUseCase
import com.linguaceleris.home.api.startWithHome
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class EmailVerificationViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        val timerFlow = MutableStateFlow(0)
        lateinit var viewModel: EmailVerificationViewModel

        fun createViewModel(fromStart: Boolean) = EmailVerificationViewModel(
            navigator = navigator,
            getEmailVerificationUseCase = getEmailVerificationUseCase,
            singOutUseCase = signOutUseCase,
            sendEmailVerificationUseCase = sendEmailVerificationUseCase,
            getSendAgainTimerUseCase = getSendAgainTimerUseCase,
            fromStart = fromStart,
        )

        beforeSpec { Dispatchers.setMain(testDispatcher) }
        afterSpec { Dispatchers.resetMain() }

        beforeEach {
            clearAllMocks()
            timerFlow.value = 0
            viewModel = createViewModel(fromStart = false)
            every { getSendAgainTimerUseCase() } returns timerFlow
        }

        Given("EmailVerificationViewModel") {

            When("initialized with fromStart = true") {
                Then("back navigation should be disabled") {
                    val viewModel = createViewModel(fromStart = true)
                    viewModel.state.value.navigationBackIsAvailable shouldBe false
                }
            }

            When("initialized with fromStart = false") {
                Then("back navigation should be enabled") {
                    val viewModel = createViewModel(fromStart = false)
                    viewModel.state.value.navigationBackIsAvailable shouldBe true
                }
            }

            When("OnBackClicked is received") {
                Then("it should navigate back") {
                    viewModel.onEvent(EmailVerificationEvent.OnBackClicked)
                    verify { navigator.back() }
                }
            }

            When("OnExitClicked is received") {
                Then("it should sign out and navigate to SignIn screen") {
                    viewModel.onEvent(EmailVerificationEvent.OnExitClicked)

                    verify { signOutUseCase() }
                    verify { navigator.startWithSignIn() }
                }
            }

            When("OnOpenMailClicked is received") {
                Then("it should send OpenEmail effect") {
                    viewModel.effect.test {
                        viewModel.onEvent(EmailVerificationEvent.OnOpenMailClicked)
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe EmailVerificationEffect.OpenEmail
                    }
                }
            }

            When("OnSendAgainClicked is received") {
                And("verification successfully sent") {
                    Then("it should handle loading state and trigger timer") {
                        val viewModel = createViewModel(fromStart = false)
                        coEvery { sendEmailVerificationUseCase() } just Runs

                        viewModel.onEvent(EmailVerificationEvent.OnSendAgainClicked)
                        viewModel.state.value.isLoading shouldBe true
                        testDispatcher.scheduler.advanceUntilIdle()

                        coVerify { sendEmailVerificationUseCase() }
                        viewModel.state.value.isLoading shouldBe false
                    }
                }
                And("verification send fails") {
                    Then("it should handle loading state and trigger timer") {
                        val viewModel = createViewModel(fromStart = false)
                        coEvery { sendEmailVerificationUseCase() } throws Exception()
                        viewModel.effect.test {

                            viewModel.onEvent(EmailVerificationEvent.OnSendAgainClicked)
                            viewModel.state.value.isLoading shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()
                            coVerify { sendEmailVerificationUseCase() }
                            viewModel.state.value.isLoading shouldBe false
                            awaitItem() shouldBe
                                EmailVerificationEffect.ShowSnackbarError(VerificationSnackbarError.SENDING)
                        }
                    }
                }
            }

            When("OnVerifyEmailClicked is received") {
                And("verification successfully sent") {
                    Then("it should show success state") {
                        val viewModel = createViewModel(fromStart = false)
                        coEvery { sendEmailVerificationUseCase() } just Runs

                        viewModel.onEvent(EmailVerificationEvent.OnVerifyEmailClicked)
                        viewModel.state.value.isLoading shouldBe true

                        testDispatcher.scheduler.advanceUntilIdle()

                        coVerify { sendEmailVerificationUseCase() }
                        viewModel.state.value.verificationState shouldBe EmailVerificationState.SUCCESS
                        viewModel.state.value.isLoading shouldBe false
                    }
                }
                And("verification send fails") {
                    Then("it should show fail state") {
                        val viewModel = createViewModel(fromStart = false)
                        coEvery { sendEmailVerificationUseCase() } throws Exception()
                        viewModel.effect.test {
                            viewModel.onEvent(EmailVerificationEvent.OnVerifyEmailClicked)

                            viewModel.state.value.isLoading shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()
                            coVerify { sendEmailVerificationUseCase() }

                            viewModel.state.value.verificationState shouldBe EmailVerificationState.INITIAL
                            viewModel.state.value.isLoading shouldBe false
                            awaitItem() shouldBe
                                EmailVerificationEffect.ShowSnackbarError(VerificationSnackbarError.SENDING)
                        }
                    }
                }
            }

            When("OnContinueClicked is received") {
                And("email is verified") {
                    Then("it should navigate to Home") {
                        coEvery { getEmailVerificationUseCase() } returns true

                        viewModel.onEvent(EmailVerificationEvent.OnContinueClicked)
                        testDispatcher.scheduler.advanceUntilIdle()

                        verify { navigator.startWithHome() }
                    }
                }

                And("email is NOT verified") {
                    Then("it should show verification dialog") {
                        coEvery { getEmailVerificationUseCase() } returns false

                        viewModel.onEvent(EmailVerificationEvent.OnContinueClicked)
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.showEmailVerificationDialog shouldBe true
                    }
                }

                And("email verification fails") {
                    Then("it should show verification error snackbar") {
                        coEvery { getEmailVerificationUseCase() } throws Exception()

                        viewModel.effect.test {
                            viewModel.onEvent(EmailVerificationEvent.OnContinueClicked)
                            testDispatcher.scheduler.advanceUntilIdle()

                            awaitItem() shouldBe EmailVerificationEffect.ShowSnackbarError(
                                VerificationSnackbarError.EMAIL,
                            )
                        }
                    }
                }
            }
            When("OnHideEmailVerificationDialog is received") {
                Then("should hide the dialog") {
                    viewModel.onEvent(EmailVerificationEvent.OnHideEmailVerificationDialog)
                    viewModel.state.value.showEmailVerificationDialog shouldBe false
                }
            }
        }
    },
)
