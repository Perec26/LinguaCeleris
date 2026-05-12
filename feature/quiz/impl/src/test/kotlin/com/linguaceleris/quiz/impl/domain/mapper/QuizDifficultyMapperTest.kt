package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.model.QuizDifficultyDTO
import com.linguaceleris.streak.model.StreakDifficultyDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class QuizDifficultyMapperTest : BehaviorSpec(
    {

        Given("QuizLevel") {
            When("toDTO is called") {
                Then("BASIC should map to QuizDifficultyDTO.BASIC") {
                    QuizLevel.BASIC.toDTO() shouldBe QuizDifficultyDTO.BASIC
                }
                Then("INTERMEDIATE should map to QuizDifficultyDTO.INTERMEDIATE") {
                    QuizLevel.INTERMEDIATE.toDTO() shouldBe QuizDifficultyDTO.INTERMEDIATE
                }
                Then("ADVANCED should map to QuizDifficultyDTO.ADVANCED") {
                    QuizLevel.ADVANCED.toDTO() shouldBe QuizDifficultyDTO.ADVANCED
                }
            }

            When("toStreakDTO is called") {
                Then("BASIC should map to StreakDifficultyDTO.BASIC") {
                    QuizLevel.BASIC.toStreakDTO() shouldBe StreakDifficultyDTO.BASIC
                }
                Then("INTERMEDIATE should map to StreakDifficultyDTO.INTERMEDIATE") {
                    QuizLevel.INTERMEDIATE.toStreakDTO() shouldBe StreakDifficultyDTO.INTERMEDIATE
                }
                Then("ADVANCED should map to StreakDifficultyDTO.ADVANCED") {
                    QuizLevel.ADVANCED.toStreakDTO() shouldBe StreakDifficultyDTO.ADVANCED
                }
            }
        }
    },
)
