package com.linguaceleris.auth.impl.ui.email

import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.linguaceleris.auth.api.EmailVerificationNavKey
import com.linguaceleris.auth.impl.navigation.ForgotPasswordNavKey
import com.linguaceleris.auth.impl.ui.email.EmailSignInMocks.getEmailVerificationUseCase
import com.linguaceleris.auth.impl.ui.email.EmailSignInMocks.navigator
import com.linguaceleris.auth.impl.ui.email.EmailSignInMocks.signInWithEmailUseCase
import com.linguaceleris.auth.impl.ui.email.EmailSignInMocks.validateEmailUseCase
import com.linguaceleris.auth.impl.ui.email.EmailSignInMocks.validatePasswordUseCase
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import com.linguaceleris.quizselection.api.QuizSelectionNavKey
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
internal class EmailSignInViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: EmailSignInViewModel

        beforeSpec { Dispatchers.setMain(testDispatcher) }
        afterSpec { Dispatchers.resetMain() }

        beforeEach {
            clearAllMocks()
            viewModel = EmailSignInViewModel(
                navigator = navigator,
                validateEmailUseCase = validateEmailUseCase,
                validatePasswordUseCase = validatePasswordUseCase,
                signInWithEmailUseCase = signInWithEmailUseCase,
                getEmailVerificationUseCase = getEmailVerificationUseCase,
            )
        }

        Given("EmailSignInViewModel") {

            When("OnBackClicked is received") {
                Then("it should navigate back") {
                    viewModel.onEvent(EmailSignInEvent.OnBackClicked)
                    verify { navigator.back() }
                }
            }

            When("OnEmailChanged is received") {
                Then("email state should be updated") {
                    val email = "test@example.com"
                    viewModel.onEvent(EmailSignInEvent.OnEmailChanged(email))
                    viewModel.state.value.email shouldBe email
                }
            }

            When("OnPasswordChanged is received") {
                Then("password state should be updated") {
                    val password = "password123"
                    viewModel.onEvent(EmailSignInEvent.OnPasswordChanged(password))
                    viewModel.state.value.password shouldBe password
                }
            }

            When("OnPasswordVisibilityChanged is received") {
                Then("isPasswordVisible should be toggled") {
                    viewModel.state.value.isPasswordVisible shouldBe false
                    viewModel.onEvent(EmailSignInEvent.OnPasswordVisibilityChanged)
                    viewModel.state.value.isPasswordVisible shouldBe true
                }
            }

            When("OnForgotPasswordClicked is received") {
                Then("it should navigate to ForgotPassword screen with current email") {
                    val email = "forgot@test.com"
                    viewModel.onEvent(EmailSignInEvent.OnEmailChanged(email))
                    viewModel.onEvent(EmailSignInEvent.OnForgotPasswordClicked)
                    verify { navigator.navigateTo(ForgotPasswordNavKey(email)) }
                }
            }

            When("OnEnterClick is received") {

                And("validation fails") {
                    Then("validation errors should be shown and sign in should not start") {
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Error.InvalidEmail
                        every { validatePasswordUseCase(any()) } returns AuthValidationResult.Success

                        viewModel.onEvent(EmailSignInEvent.OnEnterClick)

                        viewModel.state.value.validationState.email shouldBe AuthValidationResult.Error.InvalidEmail
                        coVerify(exactly = 0) { signInWithEmailUseCase(any(), any()) }
                    }
                }

                And("validation is successful") {

                    beforeEach {
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Success
                        every { validatePasswordUseCase(any()) } returns AuthValidationResult.Success
                    }

                    And("Sign in succeeds and email is verified") {
                        Then("it should show loading and navigate to QuizSelection") {
                            coEvery { signInWithEmailUseCase(any(), any()) } just Runs
                            coEvery { getEmailVerificationUseCase() } returns true

                            viewModel.onEvent(EmailSignInEvent.OnEnterClick)

                            viewModel.state.value.isLoading shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.isLoading shouldBe false
                            coVerify { signInWithEmailUseCase(any(), any()) }
                            verify { navigator.startWith(QuizSelectionNavKey) }
                        }
                    }

                    And("Sign in succeeds but email is NOT verified") {
                        Then("it should navigate to EmailVerification screen") {
                            coEvery { signInWithEmailUseCase(any(), any()) } just Runs
                            coEvery { getEmailVerificationUseCase() } returns false

                            viewModel.onEvent(EmailSignInEvent.OnEnterClick)
                            viewModel.state.value.isLoading shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()
                            viewModel.state.value.isLoading shouldBe false

                            verify { navigator.navigateTo(EmailVerificationNavKey(false)) }
                        }
                    }

                    And("Sign in fails with Invalid Credentials") {
                        Then("it should show INVALID_CREDENTIALS error") {
                            coEvery {
                                signInWithEmailUseCase(any(), any())
                            } throws mockk<FirebaseAuthInvalidUserException>()

                            viewModel.onEvent(EmailSignInEvent.OnEnterClick)
                            viewModel.state.value.isLoading shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.isLoading shouldBe false
                            viewModel.state.value.signInError shouldBe EmailSignInError.INVALID_CREDENTIALS
                        }
                    }

                    And("Sign in fails with unknown error") {
                        Then("it should show UNKNOWN_ERROR error") {
                            coEvery { signInWithEmailUseCase(any(), any()) } throws Exception()

                            viewModel.onEvent(EmailSignInEvent.OnEnterClick)
                            viewModel.state.value.isLoading shouldBe true

                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.isLoading shouldBe false
                            viewModel.state.value.signInError shouldBe EmailSignInError.UNKNOWN_ERROR
                        }
                    }
                }
            }
        }
    },
)
