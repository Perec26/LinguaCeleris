package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.model.QuizLevelDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class QuizLevelMapperTest : BehaviorSpec(
    {

        Given("QuizLevel") {
            When("toDTO is called") {
                Then("BASIC should map to QuizLevelDTO.BASIC") {
                    QuizLevel.BASIC.toDTO() shouldBe QuizLevelDTO.BASIC
                }
                Then("INTERMEDIATE should map to QuizLevelDTO.INTERMEDIATE") {
                    QuizLevel.INTERMEDIATE.toDTO() shouldBe QuizLevelDTO.INTERMEDIATE
                }
                Then("ADVANCED should map to QuizLevelDTO.ADVANCED") {
                    QuizLevel.ADVANCED.toDTO() shouldBe QuizLevelDTO.ADVANCED
                }
            }
        }
    },
)
