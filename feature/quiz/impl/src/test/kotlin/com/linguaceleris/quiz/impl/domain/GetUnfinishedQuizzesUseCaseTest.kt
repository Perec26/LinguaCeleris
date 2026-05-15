package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.auth.AuthRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.services.time.TrustedTimeManager
import com.linguaceleris.streak.StreakRepository
import com.linguaceleris.streak.model.StreakDTO
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate

internal class GetUnfinishedQuizzesUseCaseTest : BehaviorSpec(
    {
        val streakRepository = mockk<StreakRepository>()
        val authRepository = mockk<AuthRepository>()
        val timeManager = mockk<TrustedTimeManager>()
        val useCase = GetUnfinishedQuizzesUseCase(streakRepository, authRepository, timeManager)

        val userId = "user123"
        val today = LocalDate(2023, 10, 27)
        val yesterday = LocalDate(2023, 10, 26)

        beforeEach {
            every { authRepository.getCurrentUserId() } returns userId
            coEvery { timeManager.getCurrentDate() } returns today
        }

        Given("GetUnfinishedQuizzesUseCase") {
            When("user id is null") {
                Then("it should throw an IllegalStateException") {
                    every { authRepository.getCurrentUserId() } returns null
                    val exception = shouldThrow<IllegalStateException> {
                        useCase()
                    }
                    exception.message shouldBe "User id is null"
                }
            }

            When("all quizzes are unfinished (dates are null)") {
                Then("it should return all quiz levels") {
                    coEvery { streakRepository.getStreak(userId) } returns StreakDTO()
                    val result = useCase()
                    result shouldContainExactly listOf(QuizLevel.BASIC, QuizLevel.INTERMEDIATE, QuizLevel.ADVANCED)
                }
            }

            When("all quizzes are unfinished (dates are in the past)") {
                Then("it should return all quiz levels") {
                    coEvery { streakRepository.getStreak(userId) } returns StreakDTO(
                        basicLastCompletedDate = yesterday,
                        intermediateLastCompletedDate = yesterday,
                        advancedLastCompletedDate = yesterday,
                    )
                    val result = useCase()
                    result shouldContainExactly listOf(QuizLevel.BASIC, QuizLevel.INTERMEDIATE, QuizLevel.ADVANCED)
                }
            }

            When("some quizzes are finished and some are not") {
                Then("it should return only unfinished levels") {
                    coEvery { streakRepository.getStreak(userId) } returns StreakDTO(
                        basicLastCompletedDate = today,
                        intermediateLastCompletedDate = yesterday,
                        advancedLastCompletedDate = null,
                    )
                    val result = useCase()
                    result shouldContainExactly listOf(QuizLevel.INTERMEDIATE, QuizLevel.ADVANCED)
                }
            }

            When("all quizzes are finished") {
                Then("it should return an empty list") {
                    coEvery { streakRepository.getStreak(userId) } returns StreakDTO(
                        basicLastCompletedDate = today,
                        intermediateLastCompletedDate = today,
                        advancedLastCompletedDate = today,
                    )
                    val result = useCase()
                    result shouldBe emptyList()
                }
            }
        }
    },
)
