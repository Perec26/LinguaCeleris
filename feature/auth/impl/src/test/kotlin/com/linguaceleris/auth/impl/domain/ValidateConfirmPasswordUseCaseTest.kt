package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class ValidateConfirmPasswordUseCaseTest : BehaviorSpec({

    val validateConfirmPassword = ValidateConfirmPasswordUseCase()

    Given("ValidateConfirmPasswordUseCase") {
        When("confirm password is empty") {
            Then("return EmptyField error") {
                validateConfirmPassword("", "password") shouldBe AuthValidationResult.Error.EmptyField
            }
        }
        When("passwords do not match") {
            Then("return ConfirmPasswordNotMatch error") {
                validateConfirmPassword("wrong", "password") shouldBe AuthValidationResult.Error.ConfirmPasswordNotMatch
            }
        }
        When("passwords match") {
            Then("return Success") {
                validateConfirmPassword("password", "password") shouldBe AuthValidationResult.Success
            }
        }
    }
})
