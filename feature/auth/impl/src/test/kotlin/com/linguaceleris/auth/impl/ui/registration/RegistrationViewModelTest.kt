package com.linguaceleris.auth.impl.ui.registration

import app.cash.turbine.test
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.linguaceleris.auth.impl.ui.VerificationSnackbarError
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.getEmailVerificationUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.getSendAgainTimerUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.navigator
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.registerUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.sendEmailVerificationUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.validateConfirmPasswordUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.validateEmailUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.validateNicknameUseCase
import com.linguaceleris.auth.impl.ui.registration.RegistrationMocks.validatePasswordUseCase
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class RegistrationViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        val timerFlow = MutableStateFlow(0)
        lateinit var viewModel: RegistrationViewModel

        beforeSpec { Dispatchers.setMain(testDispatcher) }
        afterSpec { Dispatchers.resetMain() }

        beforeEach {
            clearAllMocks()
            timerFlow.value = 0
            every { getSendAgainTimerUseCase() } returns timerFlow

            viewModel = RegistrationViewModel(
                navigator = navigator,
                validateNicknameUseCase = validateNicknameUseCase,
                validateEmailUseCase = validateEmailUseCase,
                validatePasswordUseCase = validatePasswordUseCase,
                validateConfirmPasswordUseCase = validateConfirmPasswordUseCase,
                registerUseCase = registerUseCase,
                sendEmailVerificationUseCase = sendEmailVerificationUseCase,
                getEmailVerificationUseCase = getEmailVerificationUseCase,
                getSendAgainTimerUseCase = getSendAgainTimerUseCase,
            )
        }

        Given("RegistrationViewModel") {

            When("OnBackClicked is received") {
                Then("it should navigate back") {
                    viewModel.onEvent(RegistrationEvent.OnBackClicked)
                    verify { navigator.back() }
                }
            }

            When("user inputs fields") {
                Then("all state fields should be updated correctly") {
                    with(viewModel) {
                        onEvent(RegistrationEvent.OnNickNameChanged("dev"))
                        onEvent(RegistrationEvent.OnEmailChanged("test@test.com"))
                        onEvent(RegistrationEvent.OnPasswordChanged("pass123"))
                        onEvent(RegistrationEvent.OnConfirmPasswordChanged("pass123"))

                        with(state.value) {
                            nickname shouldBe "dev"
                            email shouldBe "test@test.com"
                            password shouldBe "pass123"
                            confirmPassword shouldBe "pass123"
                            validationState.nickname shouldBe AuthValidationResult.Success
                            validationState.email shouldBe AuthValidationResult.Success
                            validationState.password shouldBe AuthValidationResult.Success
                            validationState.confirmPassword shouldBe AuthValidationResult.Success
                        }
                    }
                }
            }

            When("user toggles password visibility") {
                Then("visibility states should flip") {
                    with(viewModel) {
                        state.value.isPasswordVisible shouldBe false
                        onEvent(RegistrationEvent.OnPasswordVisibilityChanged)
                        state.value.isPasswordVisible shouldBe true
                        onEvent(RegistrationEvent.OnPasswordVisibilityChanged)
                        state.value.isPasswordVisible shouldBe false

                        state.value.isConfirmPasswordVisible shouldBe false
                        onEvent(RegistrationEvent.OnConfirmPasswordVisibilityChanged)
                        state.value.isConfirmPasswordVisible shouldBe true
                        onEvent(RegistrationEvent.OnConfirmPasswordVisibilityChanged)
                        state.value.isPasswordVisible shouldBe false
                    }
                }
            }

            When("OnRegisterClicked is received") {

                And("validation fails for multiple fields") {
                    Then("all errors should be reflected in the state") {

                        every { validateNicknameUseCase(any()) } returns AuthValidationResult.Error.NicknameTooShort
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Error.InvalidEmail
                        every { validatePasswordUseCase(any()) } returns AuthValidationResult.Success
                        every { validateConfirmPasswordUseCase(any(), any()) } returns AuthValidationResult.Success

                        with(viewModel) {
                            onEvent(RegistrationEvent.OnRegisterClicked)

                            state.value.validationState.nickname shouldBe AuthValidationResult.Error.NicknameTooShort
                            state.value.validationState.email shouldBe AuthValidationResult.Error.InvalidEmail
                            coVerify(exactly = 0) { registerUseCase(any(), any(), any()) }
                        }
                    }
                }
                And("validation is successful") {
                    beforeEach {
                        every { validateNicknameUseCase(any()) } returns AuthValidationResult.Success
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Success
                        every { validatePasswordUseCase(any()) } returns AuthValidationResult.Success
                        every { validateConfirmPasswordUseCase(any(), any()) } returns AuthValidationResult.Success
                    }
                    And(" and registration succeeds") {
                        Then("it should update state to SUCCESS") {

                            coEvery { registerUseCase(any(), any(), any()) } just Runs

                            with(viewModel) {

                                onEvent(RegistrationEvent.OnRegisterClicked)
                                state.value.validationState.isSuccessful shouldBe true
                                state.value.isRegistrationInProgress shouldBe true

                                testDispatcher.scheduler.advanceUntilIdle()
                                state.value.registrationState shouldBe RegistrationState.SUCCESS
                                state.value.isRegistrationInProgress shouldBe false
                            }
                        }
                    }

                    And("registration fails with User Collision") {
                        Then("it should update state to USER_EXIST") {

                            coEvery { registerUseCase(any(), any(), any()) } throws
                                mockk<FirebaseAuthUserCollisionException>()

                            with(viewModel) {
                                onEvent(RegistrationEvent.OnRegisterClicked)
                                testDispatcher.scheduler.advanceUntilIdle()

                                state.value.registrationState shouldBe RegistrationState.USER_EXIST
                                state.value.isRegistrationInProgress shouldBe false
                            }
                        }
                    }
                    And("registration fails with weak password") {
                        Then("it should update state to WEAK_PASSWORD") {

                            coEvery { registerUseCase(any(), any(), any()) } throws
                                mockk<FirebaseAuthWeakPasswordException>()

                            with(viewModel) {
                                onEvent(RegistrationEvent.OnRegisterClicked)
                                testDispatcher.scheduler.advanceUntilIdle()

                                state.value.registrationState shouldBe RegistrationState.WEAK_PASSWORD
                                state.value.isRegistrationInProgress shouldBe false
                            }
                        }
                    }

                    And("registration fails with Invalid Credentials") {
                        Then("it should update state to WEAK_PASSWORD") {

                            coEvery { registerUseCase(any(), any(), any()) } throws
                                mockk<FirebaseAuthInvalidCredentialsException>()

                            with(viewModel) {
                                onEvent(RegistrationEvent.OnRegisterClicked)
                                testDispatcher.scheduler.advanceUntilIdle()

                                state.value.registrationState shouldBe RegistrationState.INVALID_CREDENTIALS
                                state.value.isRegistrationInProgress shouldBe false
                            }
                        }
                    }

                    And("registration fails with unknown error") {
                        Then("it should update state to UNKNOWN_ERROR") {

                            coEvery { registerUseCase(any(), any(), any()) } throws
                                mockk<Exception>()

                            with(viewModel) {
                                onEvent(RegistrationEvent.OnRegisterClicked)
                                testDispatcher.scheduler.advanceUntilIdle()

                                state.value.registrationState shouldBe RegistrationState.UNKNOWN_ERROR
                                state.value.isRegistrationInProgress shouldBe false
                            }
                        }
                    }
                }
            }

            When("handling email verification actions") {

                And("OnOpenMailClicked is received") {
                    Then("it should send OpenEmail effect") {
                        viewModel.effect.test {
                            viewModel.onEvent(RegistrationEvent.OnOpenMailClicked)
                            testDispatcher.scheduler.advanceUntilIdle()
                            awaitItem() shouldBe RegistrationEffect.OpenEmail
                        }
                    }
                }

                And("OnSendAgainClicked is received and succeeds") {
                    Then("it should show progress and then hide it") {
                        coEvery { sendEmailVerificationUseCase() } just Runs

                        viewModel.onEvent(RegistrationEvent.OnSendAgainClicked)
                        viewModel.state.value.isSendingVerificationInProgress shouldBe true

                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.isSendingVerificationInProgress shouldBe false
                        coVerify { sendEmailVerificationUseCase() }
                    }
                }

                And("OnSendAgainClicked fails") {
                    Then("it should show snackbar error") {
                        coEvery { sendEmailVerificationUseCase() } throws Exception()

                        viewModel.effect.test {
                            viewModel.onEvent(RegistrationEvent.OnSendAgainClicked)
                            viewModel.state.value.isSendingVerificationInProgress shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()
                            awaitItem() shouldBe RegistrationEffect.ShowSnackbarError(VerificationSnackbarError.SENDING)
                        }
                    }
                }
            }

            When("OnContinueClicked is received") {
                And("email verification succeeds") {
                    Then("it should navigate to Home") {
                        coEvery { getEmailVerificationUseCase() } returns true
                        viewModel.onEvent(RegistrationEvent.OnContinueClicked)
                        testDispatcher.scheduler.advanceUntilIdle()
                        verify { navigator.startWithHome() }
                    }
                }
                And("email not verified") {
                    Then("it should show verification dialog") {
                        coEvery { getEmailVerificationUseCase() } returns false
                        viewModel.onEvent(RegistrationEvent.OnContinueClicked)
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.showEmailVerificationDialog shouldBe true
                    }
                }
                And("email verification fails") {
                    Then("it should show snackbar error") {
                        coEvery { getEmailVerificationUseCase() } throws Exception()

                        viewModel.effect.test {
                            viewModel.onEvent(RegistrationEvent.OnContinueClicked)
                            testDispatcher.scheduler.advanceUntilIdle()

                            awaitItem() shouldBe RegistrationEffect.ShowSnackbarError(VerificationSnackbarError.EMAIL)
                        }
                    }
                }
            }
            When("OnHideEmailVerificationDialog is received") {
                Then("should hide the dialog") {
                    viewModel.onEvent(RegistrationEvent.OnHideEmailVerificationDialog)
                    viewModel.state.value.showEmailVerificationDialog shouldBe false
                }
            }

            When("timer updates from UseCase") {
                Then("state should reflect the timer value and button availability") {
                    coEvery { sendEmailVerificationUseCase() } just Runs
                    viewModel.onEvent(RegistrationEvent.OnSendAgainClicked)
                    testDispatcher.scheduler.advanceUntilIdle()
                    timerFlow.value = 30
                    testDispatcher.scheduler.runCurrent()
                    viewModel.state.value.sendAgainTimer shouldBe 30
                    viewModel.state.value.sendAgainEnable shouldBe false

                    timerFlow.value = 0
                    testDispatcher.scheduler.runCurrent()
                    viewModel.state.value.sendAgainTimer shouldBe 0
                    viewModel.state.value.sendAgainEnable shouldBe true
                }
            }
        }
    },
)
