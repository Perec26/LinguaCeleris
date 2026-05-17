package com.linguaceleris.quiz.impl.ui.quiz.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class TaskUITest : BehaviorSpec(
    {
        val word1 = WordCardUI("cat", "audio1")
        val word2 = WordCardUI("bat", "audio2")
        val trans1 = WordCardUI("кошка", "audio1_t")
        val trans2 = WordCardUI("летучая мышь", "audio2_t")

        Given("SelectCorrectAnswer") {
            val task = TaskUI.SelectCorrectAnswer(
                id = "1",
                type = TaskTypeUI.SelectTranslation,
                question = WordCardUI("Кошка", null),
                correctAnswer = word1,
                answerVariants = listOf(word1, word2),
            )

            When("no variant is selected") {
                Then("isCorrect should be false") {
                    task.isCorrect shouldBe false
                }
            }

            When("correct variant is selected") {
                val selectedTask = task.copy(selectedVariant = word1)
                Then("isCorrect should be true") {
                    selectedTask.isCorrect shouldBe true
                }
            }

            When("incorrect variant is selected") {
                val selectedTask = task.copy(selectedVariant = word2)
                Then("isCorrect should be false") {
                    selectedTask.isCorrect shouldBe false
                }
            }
        }

        Given("Matching") {
            val pairs = listOf(word1 to trans1, word2 to trans2)
            val task = TaskUI.Matching(
                id = "2",
                type = TaskTypeUI.Matching,
                pairs = pairs,
                originalVariants = listOf(word1, word2),
                translationVariants = listOf(trans1, trans2),
            )

            When("no variant is selected") {
                Then("state should be right") {
                    with(task) {
                        hasError shouldBe false
                        isDone shouldBe false
                        selectedVariantFromOriginal shouldBe false
                        selectedPair shouldBe null
                        correctVariant shouldBe null
                        isReselect(word2) shouldBe false
                        isReselect(trans1) shouldBe false
                    }
                }
            }

            When("an original variant is selected") {
                Then("state should be right") {
                    with(task.copy(selectedVariant = word1)) {
                        selectedVariantFromOriginal shouldBe true
                        selectedPair shouldBe (word1 to trans1)
                        correctVariant shouldBe trans1
                        isReselect(word2) shouldBe true
                        isReselect(trans1) shouldBe false
                    }
                }
            }

            When("a translation variant is selected") {
                Then("state should be right") {
                    with(task.copy(selectedVariant = trans1)) {
                        selectedVariantFromOriginal shouldBe false
                        selectedPair shouldBe (word1 to trans1)
                        correctVariant shouldBe word1
                        isReselect(trans2) shouldBe true
                        isReselect(word1) shouldBe false
                    }
                }
            }

            When("errorVariant is set") {
                val errorTask = task.copy(errorVariant = word1)
                Then("hasError should be true") {
                    errorTask.hasError shouldBe true
                }
            }

            When("all variants are disabled") {
                val doneTask = task.copy(disabledVariants = listOf(word1, trans1, word2, trans2))
                Then("isDone should be true") {
                    doneTask.isDone shouldBe true
                }
            }
        }
    },
)
