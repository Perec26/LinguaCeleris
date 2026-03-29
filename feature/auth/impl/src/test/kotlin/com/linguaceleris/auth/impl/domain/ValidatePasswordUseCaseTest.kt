package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class ValidatePasswordUseCaseTest : BehaviorSpec({

    val validatePassword = ValidatePasswordUseCase()

    Given("ValidatePasswordUseCase") {
        When("password is empty") {
            Then("return EmptyField error") {
                validatePassword("") shouldBe AuthValidationResult.Error.EmptyField
            }
        }
        When("password is too short") {
            Then("return PasswordTooShort error") {
                validatePassword("123") shouldBe AuthValidationResult.Error.PasswordTooShort
            }
        }
        When("password is valid") {
            Then("return Success") {
                validatePassword("StrongPassword123") shouldBe AuthValidationResult.Success
            }
        }
    }
})
