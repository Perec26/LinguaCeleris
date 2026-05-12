package com.linguaceleris.quiz.impl.ui.summary

import com.linguaceleris.home.api.backToHomeWithResult
import com.linguaceleris.quiz.api.QuizLevel
import com.linguaceleris.quiz.api.replaceWithQuiz
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.summary.QuizSummaryMocks.getUnfinishedQuizzesUseCase
import com.linguaceleris.quiz.impl.ui.summary.QuizSummaryMocks.navigator
import com.linguaceleris.quiz.impl.ui.summary.QuizSummaryMocks.playerManager
import com.linguaceleris.quiz.impl.ui.summary.QuizSummaryMocks.updateStreakUseCase
import com.linguaceleris.ui.ScreenState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class QuizSummaryViewModelTest : BehaviorSpec(
    {
        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: QuizSummaryViewModel

        fun createViewModel(quizLevel: QuizLevel = QuizLevel.BASIC, isSuccessful: Boolean = true) =
            QuizSummaryViewModel(
                navigator = navigator,
                updateStreakUseCase = updateStreakUseCase,
                getUnfinishedQuizzesUseCase = getUnfinishedQuizzesUseCase,
                playerManager = playerManager,
                quizLevel = quizLevel,
                isSuccessful = isSuccessful,
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

        Given("QuizSummaryViewModel") {
            When("initialized") {
                And("quiz is successful") {
                    And("data is loaded successfully") {
                        Then("it should update streak, load data and play success sound") {
                            val unfinishedQuizzes = listOf(QuizLevel.INTERMEDIATE)
                            coEvery { getUnfinishedQuizzesUseCase() } returns unfinishedQuizzes

                            viewModel = createViewModel(isSuccessful = true)

                            viewModel.state.value.screenState shouldBe ScreenState.LOADING
                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.result shouldBe QuizResult.SUCCESSFUL
                            viewModel.state.value.screenState shouldBe ScreenState.CONTENT
                            viewModel.state.value.unfinishedQuizzes shouldBe unfinishedQuizzes

                            coVerify { updateStreakUseCase(QuizLevel.BASIC) }
                            coVerify { playerManager.playRaw(R.raw.quiz_success) }
                        }
                    }

                    And("update streak fails") {
                        Then("it should show error") {
                            coEvery { updateStreakUseCase(any()) } throws Exception("Error")

                            viewModel = createViewModel(isSuccessful = true)
                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.result shouldBe QuizResult.SUCCESSFUL
                            viewModel.state.value.screenState shouldBe ScreenState.ERROR
                        }
                    }

                    And("getting unfinished quizzes fails") {
                        Then("it should show error") {
                            coEvery { getUnfinishedQuizzesUseCase() } throws Exception("Error")

                            viewModel = createViewModel(isSuccessful = true)
                            testDispatcher.scheduler.advanceUntilIdle()

                            viewModel.state.value.result shouldBe QuizResult.SUCCESSFUL
                            viewModel.state.value.screenState shouldBe ScreenState.ERROR
                        }
                    }
                }

                And("quiz is failed") {
                    Then("it should play failure sound") {
                        viewModel = createViewModel(isSuccessful = false)
                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.result shouldBe QuizResult.FAILURE
                        coVerify { playerManager.playRaw(R.raw.quiz_failure) }
                        viewModel.state.value.screenState shouldBe ScreenState.CONTENT
                    }
                }
            }

            When("OnTryAgainClicked event received") {
                Then("it should navigate to same quiz level") {
                    viewModel = createViewModel(quizLevel = QuizLevel.ADVANCED)
                    viewModel.onEvent(QuizSummaryEvent.OnTryAgainClicked)
                    testDispatcher.scheduler.advanceUntilIdle()

                    coVerify { navigator.replaceWithQuiz(QuizLevel.ADVANCED) }
                }
            }

            When("OnBackClick event received") {
                Then("it should navigate back to home") {
                    viewModel = createViewModel()
                    viewModel.onEvent(QuizSummaryEvent.OnBackClick)
                    testDispatcher.scheduler.advanceUntilIdle()

                    coVerify { navigator.backToHomeWithResult() }
                }
            }

            When("OnNextQuizClick event received") {
                Then("it should navigate to specified quiz level") {
                    viewModel = createViewModel()
                    testDispatcher.scheduler.advanceUntilIdle()
                    viewModel.onEvent(QuizSummaryEvent.OnNextQuizClick(QuizLevel.INTERMEDIATE))

                    coVerify { navigator.replaceWithQuiz(QuizLevel.INTERMEDIATE) }
                }
            }

            When("OnReloadData event received") {
                Then("it should reload data") {
                    viewModel = createViewModel(isSuccessful = true)
                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.onEvent(QuizSummaryEvent.OnReloadData)
                    viewModel.state.value.screenState shouldBe ScreenState.LOADING

                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.state.value.screenState shouldBe ScreenState.CONTENT
                    coVerify(exactly = 2) { getUnfinishedQuizzesUseCase() }
                }
            }
        }
    },
)
