package com.linguaceleris.home.impl.ui

import app.cash.turbine.test
import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.home.api.QuizResult
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.HomeMocks.getNextDayUseCase
import com.linguaceleris.home.impl.ui.HomeMocks.getStreakUseCase
import com.linguaceleris.home.impl.ui.HomeMocks.loadScheduleUseCase
import com.linguaceleris.home.impl.ui.HomeMocks.navigator
import com.linguaceleris.home.impl.ui.HomeMocks.setupDefaultMocks
import com.linguaceleris.home.impl.ui.HomeMocks.signOutUseCase
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.quiz.api.navigateToAdvanceQuiz
import com.linguaceleris.quiz.api.navigateToBasicQuiz
import com.linguaceleris.quiz.api.navigateToIntermediateQuiz
import com.linguaceleris.settins.api.navigateToSettings
import com.linguaceleris.ui.utils.UiText
import com.linguaceleris.ui.utils.UiTextArg
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: HomeViewModel

        fun createViewModel() = HomeViewModel(
            navigator = navigator,
            loadScheduleUseCase = loadScheduleUseCase,
            signOutUseCase = signOutUseCase,
            getStreakUseCase = getStreakUseCase,
            getNextDayUseCase = getNextDayUseCase,
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
            setupDefaultMocks()
            viewModel = createViewModel()
        }

        Given("HomeViewModel") {
            When("initialized") {
                Then("it should show data and stop loading") {
                    val streak = StreakUI.TodayNotCompleted(streak = 5)
                    coEvery { getStreakUseCase() } returns streak
                    coEvery { loadScheduleUseCase() } returns Unit

                    viewModel = createViewModel()
                    viewModel.state.value.isLoading shouldBe true

                    testDispatcher.scheduler.advanceUntilIdle()

                    coVerify { loadScheduleUseCase() }
                    coVerify { getStreakUseCase() }
                    coVerify { getNextDayUseCase() }
                    coVerify { navigator.getResultFlow<QuizResult>() }
                    viewModel.state.value.isLoading shouldBe false
                }
            }

            When("OnOpenMenuClick event received") {
                Then("it should expand menu") {

                    viewModel.onEvent(HomeEvent.OnOpenMenuClick)
                    viewModel.state.value.menuExpanded shouldBe true
                }
            }

            When("OnCloseMenuClick event received") {
                Then("it should expand menu") {

                    viewModel.onEvent(HomeEvent.OnCloseMenuClick)
                    viewModel.state.value.menuExpanded shouldBe false
                }
            }

            When("OnSettingsClick event received") {
                Then("it should navigate to settings") {
                    viewModel.onEvent(HomeEvent.OnOpenMenuClick)
                    viewModel.onEvent(HomeEvent.OnSettingsClick)
                    verify { navigator.navigateToSettings() }
                    viewModel.state.value.menuExpanded shouldBe false
                }
            }

            When("OnSignOutClick event received") {
                Then("it should sign out") {
                    viewModel.onEvent(HomeEvent.OnSignOutClick)
                    verify { signOutUseCase() }
                    verify { navigator.startWithSignIn() }
                    viewModel.state.value.menuExpanded shouldBe false
                }
            }

            When("OnBasicQuizClick event received") {
                Then("it should navigate to basic quiz") {
                    viewModel.onEvent(HomeEvent.OnBasicQuizClick)
                    verify { navigator.navigateToBasicQuiz() }
                }
            }

            When("OnIntermediateQuizClick event received") {
                Then("it should navigate to intermediate quiz") {
                    viewModel.onEvent(HomeEvent.OnIntermediateQuizClick)
                    verify { navigator.navigateToIntermediateQuiz() }
                }
            }

            When("OnAdvanceQuizClick event received") {
                Then("it should navigate to advance quiz") {
                    viewModel.onEvent(HomeEvent.OnAdvanceQuizClick)
                    verify { navigator.navigateToAdvanceQuiz() }
                }
            }

            When("OnTelegramClick event received") {
                Then("it should send effect") {
                    viewModel.effect.test {
                        viewModel.onEvent(HomeEvent.OnTelegramClick)
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe HomeEffect.OpenTelegram()
                    }
                }
            }

            When("OnYoutubeClick event received") {
                Then("it should send effect") {
                    viewModel.effect.test {
                        viewModel.onEvent(HomeEvent.OnYoutubeClick)
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe HomeEffect.OpenYoutube()
                    }
                }
            }

            When("OnRefreshClick event received") {
                And("data loading succeeds") {
                    Then("it should reload data") {
                        val streak = StreakUI.TodayNotCompleted(streak = 5)
                        coEvery { loadScheduleUseCase() } returns Unit
                        coEvery { getStreakUseCase() } returns streak

                        viewModel = createViewModel()

                        viewModel.onEvent(HomeEvent.OnRefreshClick)
                        viewModel.state.value.isLoading shouldBe true

                        testDispatcher.scheduler.advanceUntilIdle()

                        coVerify { loadScheduleUseCase() }
                        coVerify { getStreakUseCase() }
                        viewModel.state.value.isLoading shouldBe false
                        viewModel.state.value.streak shouldBe streak
                    }
                }

                And("schedule loading fails") {
                    Then("it should show error state") {
                        val streak = StreakUI.TodayNotCompleted(streak = 5)
                        coEvery { loadScheduleUseCase() } throws Exception("Failed")
                        coEvery { getStreakUseCase() } returns streak

                        viewModel = createViewModel()
                        viewModel.onEvent(HomeEvent.OnRefreshClick)
                        viewModel.state.value.isLoading shouldBe true

                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.isLoading shouldBe false
                        viewModel.state.value.hasError shouldBe true
                    }
                }

                And("streak loading fails") {
                    Then("it should show error state") {
                        coEvery { loadScheduleUseCase() } returns Unit
                        coEvery { getStreakUseCase() } throws Exception("Failed")

                        viewModel = createViewModel()
                        viewModel.onEvent(HomeEvent.OnRefreshClick)
                        viewModel.state.value.isLoading shouldBe true

                        testDispatcher.scheduler.advanceUntilIdle()

                        viewModel.state.value.isLoading shouldBe false
                        viewModel.state.value.hasError shouldBe true
                    }
                }
            }

            When("navigation result is received") {
                Then("it should reload data") {
                    val resultFlow = MutableSharedFlow<QuizResult>()
                    every { navigator.getResultFlow<QuizResult>() } returns resultFlow

                    viewModel = createViewModel()
                    resultFlow.emit(QuizResult(isSuccess = true))
                    testDispatcher.scheduler.advanceUntilIdle()

                    coVerify(atLeast = 2) { loadScheduleUseCase() }
                    coVerify(atLeast = 2) { getStreakUseCase() }
                }
            }

            When("timer receives value") {
                And("value is more than hour") {
                    Then("it should format timer correctly (HH:MM:SS)") {
                        val timerFlow = MutableSharedFlow<Duration>(replay = 1)
                        every { getNextDayUseCase() } returns timerFlow
                        viewModel = createViewModel()

                        timerFlow.emit(1.hours + 2.minutes + 3.seconds)
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.nextQuizzesTimer shouldBe UiText.DynamicString("01:02:03")
                        viewModel.state.value.lastHour shouldBe false
                    }
                }

                And("value is less than hour but more than minute") {
                    Then("it should format timer correctly (MM:SS)") {
                        val timerFlow = MutableSharedFlow<Duration>(replay = 1)
                        every { getNextDayUseCase() } returns timerFlow
                        viewModel = createViewModel()

                        timerFlow.emit(2.minutes + 3.seconds)
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.nextQuizzesTimer shouldBe UiText.DynamicString("02:03")
                        viewModel.state.value.lastHour shouldBe true
                    }
                }

                And("value is less than minute") {
                    Then("it should format timer correctly (SS)") {
                        val timerFlow = MutableSharedFlow<Duration>(replay = 1)
                        every { getNextDayUseCase() } returns timerFlow
                        viewModel = createViewModel()

                        timerFlow.emit(3.seconds)
                        testDispatcher.scheduler.advanceUntilIdle()
                        viewModel.state.value.nextQuizzesTimer shouldBe UiText.StringResource(
                            resId = R.string.home_seconds,
                            args = listOf(UiTextArg.IntArg(3)),
                        )
                        viewModel.state.value.lastHour shouldBe true
                    }
                }

                And("value is zero") {
                    Then("it should reload data") {
                        val timerFlow = MutableSharedFlow<Duration>(replay = 1)
                        every { getNextDayUseCase() } returns timerFlow
                        viewModel = createViewModel()

                        timerFlow.emit(Duration.ZERO)
                        testDispatcher.scheduler.advanceUntilIdle()
                        coVerify(atLeast = 2) { loadScheduleUseCase() }
                        coVerify(atLeast = 2) { getStreakUseCase() }
                    }
                }
            }
        }
    },
)
