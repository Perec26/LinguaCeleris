package com.linguaceleris.quiz.impl.domain.mapper

import com.linguaceleris.quiz.impl.ui.quiz.model.TaskTypeUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.model.MatchingPairDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class QuizMapperTest : BehaviorSpec(
    {

        Given("VariantDTO") {
            val dto = VariantDTO(audio = "audio", text = "cat", image = "image")

            When("toUi is called without replacement") {
                val ui = dto.toUi()
                Then("it should map all fields correctly") {
                    ui.text shouldBe dto.text
                    ui.audio shouldBe dto.audio
                    ui.image shouldBe dto.image
                }
            }

            When("toUi is called with replacement") {
                val dtoWithPattern = VariantDTO(audio = "audio", text = "The cat is on the mat", image = "image")
                val ui = dtoWithPattern.toUi(replace = "cat")

                Then("it should replace the pattern with underscores") {
                    ui.text shouldBe "The _____ is on the mat"
                }
            }
        }

        Given("MatchingPairDTO") {
            val left = VariantDTO(audio = "a1", text = "t1", image = "i1")
            val right = VariantDTO(audio = "a2", text = "t2", image = "i2")
            val dto = MatchingPairDTO(left = left, right = right)

            When("toUi is called") {
                val (leftUi, rightUi) = dto.toUi()
                Then("it should map both variants") {
                    leftUi.text shouldBe left.text
                    rightUi.text shouldBe right.text
                }
            }
        }

        Given("TaskDTO with ChooseCorrectDTO") {
            val question = VariantDTO(audio = "q_audio", text = "This is a question", image = "q_image")
            val answer = VariantDTO(audio = "a_audio", text = "question", image = "a_image")
            val options = listOf(answer, VariantDTO(audio = "o_audio", text = "option", image = "o_image"))
            val data = TaskDataDTO.ChooseCorrectDTO(
                question = question,
                answer = answer,
                options = options,
            )

            When("it is SelectTranslationDTO") {
                val dto = TaskDTO.SelectTranslationDTO(id = "1", data = data)
                val ui = dto.toUi()

                Then("it should map to SelectCorrectAnswer UI model") {
                    ui.shouldBeInstanceOf<TaskUI.SelectCorrectAnswer>()
                    ui.id shouldBe "1"
                    ui.type shouldBe TaskTypeUI.SelectTranslation
                    ui.question.text shouldBe question.text
                    ui.correctAnswer.text shouldBe answer.text
                    ui.answerVariants.size shouldBe options.size
                }
            }

            When("it is FillInTheBlankDTO") {
                val dto = TaskDTO.FillInTheBlankDTO(id = "2", data = data)
                val ui = dto.toUi()

                Then("it should map with replacement in question") {
                    ui.shouldBeInstanceOf<TaskUI.SelectCorrectAnswer>()
                    ui.type shouldBe TaskTypeUI.FillInBlank
                    ui.question.text shouldBe "This is a _____"
                }
            }
        }

        Given("TaskDTO with MatchingDataDTO") {
            val pair = MatchingPairDTO(
                left = VariantDTO(audio = "a1", text = "t1", image = "i1"),
                right = VariantDTO(audio = "a2", text = "t2", image = "i2"),
            )
            val data = TaskDataDTO.MatchingDataDTO(pairs = listOf(pair))
            val dto = TaskDTO.MatchingDTO(id = "3", data = data)

            When("toUi is called") {
                val ui = dto.toUi()

                Then("it should map to Matching UI model") {
                    ui.shouldBeInstanceOf<TaskUI.Matching>()
                    ui.id shouldBe "3"
                    ui.type shouldBe TaskTypeUI.Matching
                    ui.pairs.size shouldBe 1
                    ui.originalVariants.size shouldBe 1
                    ui.translationVariants.size shouldBe 1
                }
            }
        }

        Given("TaskDTO with UnknownDTO") {
            val dto = TaskDTO.UnknowQuestionDTO(id = "4", data = TaskDataDTO.UnknownDTO)

            When("toUi is called") {
                val ui = dto.toUi()
                Then("it should return null") {
                    ui shouldBe null
                }
            }
        }

        Given("List of TaskDTO") {
            val dtos = listOf(
                TaskDTO.UnknowQuestionDTO(id = "1", data = TaskDataDTO.UnknownDTO),
                TaskDTO.MatchingDTO(id = "2", data = TaskDataDTO.MatchingDataDTO(emptyList())),
            )

            When("toUi is called on list") {
                val uis = dtos.toUi()
                Then("it should map only valid tasks") {
                    uis.size shouldBe 1
                    uis.first().id shouldBe "2"
                }
            }
        }
    },
)
