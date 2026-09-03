package com.linguaceleris.quiz.impl.domain

import app.cash.turbine.test
import com.linguaceleris.lib.ProgressWrapper
import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow

internal class GetTasksUseCaseTest : BehaviorSpec(
    {

        fun createTaskDTO(id: String) = TaskDTO.SelectTranslationDTO(
            id = id,
            data = TaskDataDTO.ChooseCorrectDTO(
                question = VariantDTO(null, "q_$id", null),
                answer = VariantDTO(null, "a_$id", null),
                options = emptyList(),
            ),
        )

        Given("GetTasksUseCase") {
            val repository = mockk<QuizRepository>()
            val useCase = GetTasksUseCase(repository)

            When("repository returns Error") {
                val exception = IllegalStateException("Quiz is null")
                Then("it should map to Error") {
                    coEvery { repository.getTasks(any()) } returns flow {
                        emit(ProgressWrapper.Failure(exception))
                    }

                    useCase.invoke(QuizLevel.BASIC).test {
                        awaitItem() shouldBe ProgressWrapper.Failure(exception)
                        awaitComplete()
                    }
                }
            }

            When("repository returns loading") {
                Then("it should map to loading") {
                    coEvery { repository.getTasks(any()) } returns flow {
                        emit(ProgressWrapper.Loading(0.5f))
                    }

                    useCase.invoke(QuizLevel.BASIC).test {
                        awaitItem() shouldBe ProgressWrapper.Loading(0.5f)
                        awaitComplete()
                    }
                }
            }

            When("repository returns enough tasks (>= 12)") {
                val tasks = List(15) { createTaskDTO(it.toString()) }
                val quiz = QuizDTO(
                    contractVersion = "1",
                    dailyQuizId = "id",
                    extraPool = emptyList(),
                    tasks = tasks,
                )
                coEvery { repository.getTasks(any()) } returns flow { emit(ProgressWrapper.Success(quiz)) }

                Then("it should return all tasks") {

                    useCase.invoke(QuizLevel.BASIC).test {
                        awaitItem().shouldBeInstanceOf<ProgressWrapper.Success<List<TaskUI>>> { quiz ->
                            quiz.value shouldHaveSize 15
                            quiz.value.map { it.id }.shouldContainAll(tasks.map { it.id })
                        }
                        awaitComplete()
                    }
                }
            }

            When("repository returns not enough tasks (< 12) but has extra pool") {
                val mainTasks = List(5) { createTaskDTO("main_$it") }
                val extraTasks = List(10) { createTaskDTO("extra_$it") }
                val quiz = QuizDTO(
                    contractVersion = "1",
                    dailyQuizId = "id",
                    extraPool = extraTasks,
                    tasks = mainTasks,
                )
                coEvery { repository.getTasks(any()) } returns flow { emit(ProgressWrapper.Success(quiz)) }

                Then("it should supplement tasks from extra pool to reach 12") {
                    useCase.invoke(QuizLevel.BASIC).test {
                        awaitItem().shouldBeInstanceOf<ProgressWrapper.Success<List<TaskUI>>> { quiz ->
                            quiz.value shouldHaveSize 12
                            val resultIds = quiz.value.map { it.id }
                            resultIds.filter { it.startsWith("main_") } shouldHaveSize 5
                            resultIds.filter { it.startsWith("extra_") } shouldHaveSize 7
                        }
                        awaitComplete()
                    }
                }
            }

            When("repository returns not enough tasks and extra pool is small") {
                val mainTasks = List(5) { createTaskDTO("main_$it") }
                val extraTasks = List(3) { createTaskDTO("extra_$it") }
                val quiz = QuizDTO(
                    contractVersion = "1",
                    dailyQuizId = "id",
                    extraPool = extraTasks,
                    tasks = mainTasks,
                )
                coEvery { repository.getTasks(any()) } returns flow { emit(ProgressWrapper.Success(quiz)) }

                Then("it should return all available tasks") {

                    useCase.invoke(QuizLevel.BASIC).test {
                        awaitItem().shouldBeInstanceOf<ProgressWrapper.Success<List<TaskUI>>> { quiz ->
                            quiz.value shouldHaveSize 8
                            val allIds = (mainTasks + extraTasks).map { it.id }
                            quiz.value.map { it.id } shouldContainAll allIds
                        }
                        awaitComplete()
                    }
                }
            }
        }
    },
)
