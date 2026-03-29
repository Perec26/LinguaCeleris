package com.linguaceleris.auth.impl.domain

import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class ValidateNicknameUseCaseTest : BehaviorSpec({

    val validateNickname = ValidateNicknameUseCase()

    Given("ValidateNicknameUseCase") {
        When("nickname is empty") {
            Then("return EmptyField error") {
                validateNickname("") shouldBe AuthValidationResult.Error.EmptyField
            }
        }
        When("nickname is too short") {
            Then("return NicknameTooShort error") {
                validateNickname("a") shouldBe AuthValidationResult.Error.NicknameTooShort
            }
        }
        When("nickname is too long") {
            Then("return NicknameTooLong error") {
                validateNickname("a".repeat(21)) shouldBe AuthValidationResult.Error.NicknameTooLong
            }
        }
        When("nickname is valid") {
            Then("return Success") {
                validateNickname("ValidUser") shouldBe AuthValidationResult.Success
            }
        }
    }
})
