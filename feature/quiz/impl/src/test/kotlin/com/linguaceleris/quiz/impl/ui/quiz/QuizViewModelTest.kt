package com.linguaceleris.quiz.impl.ui.quiz

import com.linguaceleris.lib.ProgressWrapper
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.navigation.navigateToSummary
import com.linguaceleris.quiz.impl.ui.quiz.QuizMocks.getTasksUseCase
import com.linguaceleris.quiz.impl.ui.quiz.QuizMocks.navigator
import com.linguaceleris.quiz.impl.ui.quiz.QuizMocks.playerManager
import com.linguaceleris.quiz.impl.ui.quiz.QuizMocks.selectVariantUseCase
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.ui.ScreenState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class QuizViewModelTest : BehaviorSpec(
    {
        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: QuizViewModel

        fun createViewModel(level: QuizLevel = QuizLevel.BASIC) = QuizViewModel(
            navigator = navigator,
            getTasksUseCase = getTasksUseCase,
            playerManager = playerManager,
            selectVariantUseCase = selectVariantUseCase,
            quizLevel = level,
        )

        beforeSpec {
            Dispatchers.setMain(testDispatcher)
        }

        afterSpec {
            Dispatchers.resetMain()
            unmockkAll()
        }

        beforeEach {
            clearAllMocks()
        }

        Given("QuizViewModel") {
            When("initialized") {
                Then("it should load tasks and show content") {
                    val tasks = listOf(taskSelectCorrectAnswerMock)
                    coEvery { getTasksUseCase(any()) } returns flow { emit(ProgressWrapper.Success(tasks)) }

                    viewModel = createViewModel()
                    viewModel.state.value.screenState shouldBe ScreenState.LOADING

                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.state.value.screenState shouldBe ScreenState.CONTENT
                    viewModel.state.value.tasks shouldBe tasks
                    viewModel.state.value.currentTask shouldBe tasks.first()
                }

                And("loading fails") {
                    Then("it should show error state") {
                        coEvery { getTasksUseCase(any()) } throws Exception("Failed")

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.screenState shouldBe ScreenState.ERROR
                    }
                }
            }

            When("OnAudioClick event received") {
                Then("it should play audio") {
                    viewModel = createViewModel()
                    val audioUrl = "http://audio.mp3"
                    viewModel.onEvent(QuizEvent.OnAudioClick(audioUrl))

                    verify { playerManager.playUrl(audioUrl) }
                }
            }

            When("SelectAnswer event received") {
                Then("it should update selected variant") {
                    val initialTask = taskSelectCorrectAnswerMock
                    val variant = correctAnswerMock
                    val updatedTask = initialTask.copy(selectedVariant = variant)

                    coEvery { getTasksUseCase(any()) } returns flow {
                        emit(ProgressWrapper.Success(listOf(initialTask)))
                    }
                    every { selectVariantUseCase(initialTask, variant) } returns updatedTask

                    viewModel = createViewModel()
                    testDispatcher.scheduler.advanceUntilIdle()
                    viewModel.onEvent(QuizEvent.SelectAnswer(variant))

                    verify { selectVariantUseCase(initialTask, variant) }

                    viewModel.state.value.currentTask shouldBe updatedTask
                }

                And("task is Matching and has error") {
                    Then("it should play error sound") {
                        val initialTask = taskMatchingMock.copy(selectedVariant = correctAnswerMock)
                        val variant = incorrectAnswerMock
                        val updatedTask = initialTask.copy(errorVariant = variant)

                        coEvery { getTasksUseCase(any()) } returns flow {
                            emit(ProgressWrapper.Success(listOf(initialTask)))
                        }
                        every { selectVariantUseCase(initialTask, variant) } returns updatedTask

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.onEvent(QuizEvent.SelectAnswer(variant))

                        verify { playerManager.playRaw(R.raw.quiz_error) }
                    }
                }

                And("task is Matching and is done") {
                    Then("it should play correct sound") {
                        val initialTask = taskMatchingMock.copy(selectedVariant = correctAnswerMock)
                        val variant = correctAnswerMock
                        val updatedTask = initialTask.copy(
                            selectedVariant = null,
                            disabledVariants = initialTask.pairs.flatMap { listOf(it.first, it.second) },
                        )

                        coEvery { getTasksUseCase(any()) } returns flow {
                            emit(ProgressWrapper.Success(listOf(initialTask)))
                        }
                        every { selectVariantUseCase(initialTask, variant) } returns updatedTask

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.onEvent(QuizEvent.SelectAnswer(variant))

                        verify { playerManager.playRaw(R.raw.quiz_correct) }
                    }
                }
            }

            When("OnCheckButtonClick event received") {
                And("answer is correct") {
                    Then("it should play correct sound and update state") {
                        val task = taskSelectCorrectAnswerMock.copy(selectedVariant = correctAnswerMock)
                        coEvery { getTasksUseCase(any()) } returns flow { emit(ProgressWrapper.Success(listOf(task))) }

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.onEvent(QuizEvent.OnCheckButtonClick)

                        verify { playerManager.stop() }
                        verify { playerManager.playRaw(R.raw.quiz_correct) }
                        (viewModel.state.value.currentTask as TaskUI.SelectCorrectAnswer).isChecked shouldBe true
                    }
                }

                And("answer is incorrect") {
                    Then("it should play error sound and decrease lives") {
                        val task = selectCorrectAnswerMock.copy(selectedVariant = incorrectAnswerMock)
                        coEvery { getTasksUseCase(any()) } returns flow { emit(ProgressWrapper.Success(listOf(task))) }

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        val initialLives = viewModel.state.value.lives
                        viewModel.onEvent(QuizEvent.OnCheckButtonClick)

                        verify { playerManager.stop() }
                        verify { playerManager.playRaw(R.raw.quiz_error) }
                        viewModel.state.value.lives shouldBe initialLives - 1
                    }
                }
            }

            When("OnContinueButtonClick event received") {
                And("it is the last task") {
                    Then("it should navigate to summary with success") {
                        val task = taskSelectCorrectAnswerMock
                        coEvery { getTasksUseCase(any()) } returns flow { emit(ProgressWrapper.Success(listOf(task))) }

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.onEvent(QuizEvent.OnContinueButtonClick)

                        verify { playerManager.stop() }
                        verify { navigator.navigateToSummary(QuizLevel.BASIC, true) }
                    }
                }

                And("no lives left") {
                    Then("it should navigate to summary with failure") {
                        val task = taskSelectCorrectAnswerMock.copy(selectedVariant = incorrectAnswerMock)
                        coEvery { getTasksUseCase(any()) } returns flow {
                            emit(ProgressWrapper.Success(listOf(task, task)))
                        }

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        repeat(3) { viewModel.onEvent(QuizEvent.OnCheckButtonClick) }

                        viewModel.onEvent(QuizEvent.OnContinueButtonClick)

                        verify { navigator.navigateToSummary(QuizLevel.BASIC, false) }
                    }
                }

                And("there are more tasks and lives") {
                    Then("it should show next task") {
                        val task1 = selectCorrectAnswerMock.copy(id = "1")
                        val task2 = selectCorrectAnswerMock.copy(id = "2")
                        coEvery { getTasksUseCase(any()) } returns flow {
                            emit(ProgressWrapper.Success(listOf(task1, task2)))
                        }

                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.onEvent(QuizEvent.OnContinueButtonClick)

                        viewModel.state.value.currentTaskIndex shouldBe 1
                        viewModel.state.value.currentTask shouldBe task2
                    }
                }
            }

            When("OnBackClick event received") {
                And("screen state is CONTENT") {
                    Then("it should show exit dialog") {
                        coEvery { getTasksUseCase(any()) } returns flow {
                            emit(ProgressWrapper.Success(listOf(selectCorrectAnswerMock)))
                        }
                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.onEvent(QuizEvent.OnBackClick)

                        viewModel.state.value.showExitDialog shouldBe true
                    }
                }

                And("screen state is NOT CONTENT") {
                    Then("it should navigate back") {
                        coEvery { getTasksUseCase(any()) } throws Exception()
                        viewModel = createViewModel()
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.onEvent(QuizEvent.OnBackClick)
                        verify { navigator.back() }
                    }
                }
            }

            When("OnExitConfirmClick event received") {
                Then("it should hide dialog and navigate back") {
                    viewModel = createViewModel()
                    testDispatcher.scheduler.advanceUntilIdle()
                    viewModel.onEvent(QuizEvent.OnExitConfirmClick)

                    viewModel.state.value.showExitDialog shouldBe false
                    verify { navigator.back() }
                }
            }

            When("OnExitCancelClick event received") {
                Then("it should hide dialog") {
                    viewModel = createViewModel()
                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.onEvent(QuizEvent.OnExitCancelClick)

                    viewModel.state.value.showExitDialog shouldBe false
                }
            }

            When("OnReloadClick event received") {
                Then("it should reload tasks") {
                    val tasks = listOf(selectCorrectAnswerMock)
                    coEvery { getTasksUseCase(any()) } returns flow { emit(ProgressWrapper.Success(tasks)) }

                    viewModel = createViewModel()

                    viewModel.onEvent(QuizEvent.OnReloadClick)
                    testDispatcher.scheduler.advanceUntilIdle()

                    with(viewModel.state.value) {
                        screenState shouldBe ScreenState.CONTENT
                        tasks shouldBe tasks
                        currentTask shouldBe tasks.first()
                    }
                }
            }
        }
    },
)
