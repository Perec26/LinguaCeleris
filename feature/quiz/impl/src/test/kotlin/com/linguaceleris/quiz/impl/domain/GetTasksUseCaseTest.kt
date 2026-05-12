package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

internal class GetTasksUseCaseTest : BehaviorSpec({

    fun createTaskDTO(id: String) = TaskDTO.SelectTranslationDTO(
        id = id,
        data = TaskDataDTO.ChooseCorrectDTO(
            question = VariantDTO(null, "q_$id", null),
            answer = VariantDTO(null, "a_$id", null),
            options = emptyList()
        )
    )

    Given("GetTasksUseCase") {
        val repository = mockk<QuizRepository>()
        val useCase = GetTasksUseCase(repository)

        When("repository returns null") {
            coEvery { repository.getTasks(any()) } returns null
            Then("it should throw an IllegalStateException") {
                val exception = shouldThrow<IllegalStateException> { useCase(QuizLevel.BASIC) }
                exception.message shouldBe "Quiz is null"
            }
        }

        When("repository returns enough tasks (>= 12)") {
            val tasks = List(15) { createTaskDTO(it.toString()) }
            val quiz = QuizDTO(
                contractVersion = "1",
                dailyQuizId = "id",
                extraPool = emptyList(),
                tasks = tasks
            )
            coEvery { repository.getTasks(any()) } returns quiz

            val result = useCase(QuizLevel.BASIC)
            Then("it should return all tasks") {
                result shouldHaveSize 15
                result.map { it.id }.shouldContainAll(tasks.map { it.id })
            }
        }

        When("repository returns not enough tasks (< 12) but has extra pool") {
            val mainTasks = List(5) { createTaskDTO("main_$it") }
            val extraTasks = List(10) { createTaskDTO("extra_$it") }
            val quiz = QuizDTO(
                contractVersion = "1",
                dailyQuizId = "id",
                extraPool = extraTasks,
                tasks = mainTasks
            )
            coEvery { repository.getTasks(any()) } returns quiz

            val result = useCase(QuizLevel.BASIC)
            Then("it should supplement tasks from extra pool to reach 12") {
                result shouldHaveSize 12
                val resultIds = result.map { it.id }
                resultIds.filter { it.startsWith("main_") } shouldHaveSize 5
                resultIds.filter { it.startsWith("extra_") } shouldHaveSize 7
            }
        }

        When("repository returns not enough tasks and extra pool is small") {
            val mainTasks = List(5) { createTaskDTO("main_$it") }
            val extraTasks = List(3) { createTaskDTO("extra_$it") }
            val quiz = QuizDTO(
                contractVersion = "1",
                dailyQuizId = "id",
                extraPool = extraTasks,
                tasks = mainTasks
            )
            coEvery { repository.getTasks(any()) } returns quiz

            val result = useCase(QuizLevel.BASIC)
            Then("it should return all available tasks") {
                result shouldHaveSize 8
                val allIds = (mainTasks + extraTasks).map { it.id }
                result.map { it.id } shouldContainAll allIds
            }
        }
    }
})
