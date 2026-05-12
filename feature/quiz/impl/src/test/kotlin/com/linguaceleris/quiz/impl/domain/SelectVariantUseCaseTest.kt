package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.impl.ui.quiz.model.TaskTypeUI
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

internal class SelectVariantUseCaseTest : BehaviorSpec({
    val useCase = SelectVariantUseCase()

    val word1 = WordCardUI("cat", "audio1")
    val word2 = WordCardUI("bat", "audio2")
    val trans1 = WordCardUI("кошка", "audio1_t")
    val trans2 = WordCardUI("летучая мышь", "audio2_t")

    Given("SelectCorrectAnswer task") {
        val task = TaskUI.SelectCorrectAnswer(
            id = "1",
            type = TaskTypeUI.SelectTranslation,
            question = WordCardUI("Кошка", null),
            correctAnswer = word1,
            answerVariants = listOf(word1, word2)
        )

        When("a variant is selected") {
            val result = useCase(task, word1) as TaskUI.SelectCorrectAnswer
            Then("it should update selectedVariant") {
                result.selectedVariant shouldBe word1
            }
        }
    }

    Given("Matching task") {
        val pairs = listOf(word1 to trans1, word2 to trans2)
        val task = TaskUI.Matching(
            id = "2",
            type = TaskTypeUI.Matching,
            pairs = pairs,
            originalVariants = listOf(word1, word2),
            translationVariants = listOf(trans1, trans2)
        )

        When("no variant is selected and a variant is clicked") {
            val result = useCase(task, word1) as TaskUI.Matching
            Then("it should set it as selectedVariant") {
                result.selectedVariant shouldBe word1
            }
        }

        When("a variant is already selected") {
            val taskWithSelection = task.copy(selectedVariant = word1)

            And("the same side variant is clicked (reselect)") {
                val result = useCase(taskWithSelection, word2) as TaskUI.Matching
                Then("it should update selectedVariant to the new one") {
                    result.selectedVariant shouldBe word2
                }
            }

            And("the matching variant is clicked") {
                val result = useCase(taskWithSelection, trans1) as TaskUI.Matching
                Then("it should disable both variants and clear selection") {
                    result.disabledVariants shouldContainAll listOf(word1, trans1)
                    result.selectedVariant shouldBe null
                }
            }

            And("a non-matching variant from the other side is clicked") {
                val result = useCase(taskWithSelection, trans2) as TaskUI.Matching
                Then("it should set errorVariant") {
                    result.errorVariant shouldBe trans2
                    result.selectedVariant shouldBe word1
                }
            }
        }
    }
})
