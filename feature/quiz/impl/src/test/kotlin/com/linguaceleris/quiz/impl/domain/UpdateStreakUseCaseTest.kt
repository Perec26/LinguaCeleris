package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.streak.StreakRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

internal class UpdateStreakUseCaseTest : BehaviorSpec({
    val authRepository = mockk<AuthRepository>()
    val streakRepository = mockk<StreakRepository>(relaxed = true)
    val useCase = UpdateStreakUseCase(authRepository, streakRepository)

    Given("UpdateStreakUseCase") {
        val userId = "test_user_id"

        When("user id is null") {
            coEvery { authRepository.getCurrentUserId() } returns null

            Then("it should throw IllegalStateException") {
                shouldThrow<IllegalStateException> { useCase(QuizLevel.BASIC) }
            }
        }

        When("level is BASIC") {
            coEvery { authRepository.getCurrentUserId() } returns userId

            useCase(QuizLevel.BASIC)

            Then("it should update basic streak") {
                coVerify { streakRepository.updateBasicStreak(userId) }
            }
        }

        When("level is INTERMEDIATE") {
            coEvery { authRepository.getCurrentUserId() } returns userId

            useCase(QuizLevel.INTERMEDIATE)

            Then("it should update intermediate streak") {
                coVerify { streakRepository.updateIntermediateStreak(userId) }
            }
        }

        When("level is ADVANCED") {
            coEvery { authRepository.getCurrentUserId() } returns userId

            useCase(QuizLevel.ADVANCED)

            Then("it should update advanced streak") {
                coVerify { streakRepository.updateAdvancedStreak(userId) }
            }
        }
    }
})
