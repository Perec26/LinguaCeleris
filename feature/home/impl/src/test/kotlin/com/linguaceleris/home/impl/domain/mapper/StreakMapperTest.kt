package com.linguaceleris.home.impl.domain.mapper

import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.model.QuizCompletionUI
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.streak.model.StreakDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.datetime.LocalDate

internal class StreakMapperTest : BehaviorSpec(
    {

        val currentDate = LocalDate(2025, 1, 10)

        Given("StreakDTO") {

            When("longest streak is 0") {
                val dto = StreakDTO(longest = 0)
                Then("it should map to NeverStarted") {
                    dto.toUi(currentDate) shouldBe StreakUI.NeverStarted
                }
            }

            When("daysWithoutCompleted is 0 (today is completed)") {
                val dto = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = currentDate,
                    intermediateLastCompletedDate = null,
                    advancedLastCompletedDate = null,
                )
                val result = dto.toUi(currentDate)

                Then("it should map to TodayCompleted") {
                    result.shouldBeInstanceOf<StreakUI.TodayCompleted>()
                    result.streak shouldBe 5
                    result.completion shouldBe QuizCompletionUI(
                        basicIsCompleted = true,
                        intermediateIsCompleted = false,
                        advancedIsCompleted = false,
                    )
                }
            }

            When("today is completed with specific") {
                val dto = StreakDTO(
                    current = 7,
                    longest = 10,
                    basicLastCompletedDate = currentDate,
                )
                val daysAndRes = mapOf(
                    1 to R.string.home_streak_completed_1,
                    2 to R.string.home_streak_completed_3,
                    3 to R.string.home_streak_completed_3,
                    7 to R.string.home_streak_completed_7,
                    14 to R.string.home_streak_completed_14,
                    30 to R.string.home_streak_completed_30,
                    50 to R.string.home_streak_completed_50,
                    100 to R.string.home_streak_completed_100,
                    365 to R.string.home_streak_completed_365,
                )
                daysAndRes.forEach { (daysWithoutCompleted, subtitle) ->

                    And("daysWithoutCompleted is $daysWithoutCompleted") {
                        val result = dto.copy(current = daysWithoutCompleted).toUi(currentDate)
                        Then("it should have correct subtitle") {
                            result.shouldBeInstanceOf<StreakUI.TodayCompleted>()
                            result.subtitle shouldBe subtitle
                        }
                    }
                }
            }

            When("daysWithoutCompleted is 1 (yesterday was completed)") {
                val dto = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = LocalDate(2025, 1, 9),
                )
                Then("it should map to TodayNotCompleted") {
                    dto.toUi(currentDate) shouldBe StreakUI.TodayNotCompleted(5)
                }
            }

            When("daysWithoutCompleted is 2 (missed 1 day)") {
                val dto = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = LocalDate(2025, 1, 8),
                )
                Then("it should map to OneFreeze") {
                    dto.toUi(currentDate) shouldBe StreakUI.Freeze.OneFreeze
                }
            }

            When("daysWithoutCompleted is 3 (missed 2 days)") {
                val dto = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = LocalDate(2025, 1, 7),
                )
                Then("it should map to NoneFreeze") {
                    dto.toUi(currentDate) shouldBe StreakUI.Freeze.NoneFreeze
                }
            }

            When("daysWithoutCompleted is 4 (missed 3 days)") {
                val dto = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = LocalDate(2025, 1, 6),
                )
                Then("it should map to Dead") {
                    dto.toUi(currentDate) shouldBe StreakUI.Dead(longestStreak = 10)
                }
            }

            When("multiple difficulties are completed") {
                val dto = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = currentDate,
                    intermediateLastCompletedDate = LocalDate(2025, 1, 9),
                    advancedLastCompletedDate = currentDate,
                )
                val result = dto.toUi(currentDate)
                Then("completion status should reflect today's progress") {
                    result.completion shouldBe QuizCompletionUI(
                        basicIsCompleted = true,
                        intermediateIsCompleted = false,
                        advancedIsCompleted = true,
                    )
                }
            }
        }
    },
)
