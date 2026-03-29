package com.linguaceleris.auth.impl.domain

import android.util.Patterns
import com.linguaceleris.auth.impl.ui.registration.model.AuthValidationResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic

internal class ValidateEmailUseCaseTest : BehaviorSpec({

    val validateEmail = ValidateEmailUseCase()

    beforeSpec {
        mockkStatic(Patterns::class)
    }

    afterSpec {
        unmockkStatic(Patterns::class)
    }

    Given("ValidateEmailUseCase") {
        When("email is empty") {
            Then("return EmptyField error") {
                validateEmail("") shouldBe AuthValidationResult.Error.EmptyField
            }
        }
        When("email is invalid") {
            Then("return InvalidEmail error") {
                every { Patterns.EMAIL_ADDRESS.matcher(any()).matches() } returns false
                validateEmail("invalid-email") shouldBe AuthValidationResult.Error.InvalidEmail
            }
        }
        When("email is valid") {
            Then("return Success") {
                every { Patterns.EMAIL_ADDRESS.matcher(any()).matches() } returns true
                validateEmail("test@example.com") shouldBe AuthValidationResult.Success
            }
        }
    }
})
