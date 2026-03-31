package com.linguaceleris.auth.impl.ui.forgotpassword

import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.auth.impl.ui.forgotpassword.ForgotPasswordMocks.navigator
import com.linguaceleris.auth.impl.ui.forgotpassword.ForgotPasswordMocks.resetPasswordUseCase
import com.linguaceleris.auth.impl.ui.forgotpassword.ForgotPasswordMocks.validateEmailUseCase
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
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
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class ForgotPasswordViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        val initialEmail = "test@example.com"
        lateinit var viewModel: ForgotPasswordViewModel

        beforeSpec { Dispatchers.setMain(testDispatcher) }
        afterSpec { Dispatchers.resetMain() }

        beforeEach {
            clearAllMocks()
            viewModel = ForgotPasswordViewModel(
                navigator = navigator,
                resetPasswordUseCase = resetPasswordUseCase,
                validateEmailUseCase = validateEmailUseCase,
                email = initialEmail,
            )
        }

        Given("ForgotPasswordViewModel") {

            When("initialized") {
                Then("state should contain the initial email") {
                    viewModel.state.value.email shouldBe initialEmail
                }
            }

            When("OnEmailChanged event is received") {
                Then("email in state should be updated") {
                    val newEmail = "new@example.com"
                    viewModel.onEvent(ForgotPasswordEvent.OnEmailChanged(newEmail))
                    viewModel.state.value.email shouldBe newEmail
                }
            }

            When("OnResetClick event is received") {

                And("validation fails") {
                    Then("validation error should be shown") {
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Error.InvalidEmail

                        viewModel.onEvent(ForgotPasswordEvent.OnResetClick)
                        viewModel.state.value.validationState.email shouldBe AuthValidationResult.Error.InvalidEmail
                        coVerify(exactly = 0) { resetPasswordUseCase(any()) }
                    }
                }

                And("validation is successful") {
                    Then("it should show loading, call use case and show success state") {
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Success
                        coEvery { resetPasswordUseCase(any()) } just Runs

                        viewModel.onEvent(ForgotPasswordEvent.OnResetClick)
                        viewModel.state.value.isLoading shouldBe true
                        testDispatcher.scheduler.advanceUntilIdle()

                        coVerify { resetPasswordUseCase(initialEmail) }
                        viewModel.state.value.isLoading shouldBe false
                        viewModel.state.value.showSuccess shouldBe true
                    }
                }
            }

            When("OnBackClicked event is received") {

                And("success state is NOT shown") {
                    Then("it should navigate back") {
                        viewModel.onEvent(ForgotPasswordEvent.OnBackClicked)
                        verify { navigator.back() }
                    }
                }

                And("success state IS shown") {
                    Then("it should navigate to SignIn screen") {
                        every { validateEmailUseCase(any()) } returns AuthValidationResult.Success
                        coEvery { resetPasswordUseCase(any()) } just Runs
                        viewModel.onEvent(ForgotPasswordEvent.OnResetClick)
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.onEvent(ForgotPasswordEvent.OnBackClicked)
                        verify { navigator.startWithSignIn() }
                    }
                }
            }
        }
    },
)
