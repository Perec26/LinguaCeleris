package com.linguaceleris.quiz

import app.cash.turbine.test
import com.linguaceleris.lib.ProgressWrapper
import com.linguaceleris.quiz.QuizDataMocks.audioLoadService
import com.linguaceleris.quiz.QuizDataMocks.dataSource
import com.linguaceleris.quiz.QuizDataMocks.dateMock
import com.linguaceleris.quiz.QuizDataMocks.imageLoadService
import com.linguaceleris.quiz.QuizDataMocks.levelMock
import com.linguaceleris.quiz.QuizDataMocks.quizIdMock
import com.linguaceleris.quiz.QuizDataMocks.quizWithMatchingMock
import com.linguaceleris.quiz.QuizDataMocks.scheduleMock
import com.linguaceleris.quiz.QuizDataMocks.storage
import com.linguaceleris.quiz.QuizDataMocks.timeManager
import com.linguaceleris.quiz.model.QuizDTO
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class QuizRepositoryTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        lateinit var repository: QuizRepository

        fun createRepository() = QuizRepository(
            imageLoadService = imageLoadService,
            audioLoadService = audioLoadService,
            timeManager = timeManager,
            dataSource = dataSource,
            storage = storage,
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
            repository = createRepository()
        }

        Given("QuizRepository") {

            When("loadSchedule is called") {
                And("dataSource returns schedule") {
                    Then("it should save schedule to storage") {
                        coEvery { dataSource.getSchedule() } returns scheduleMock
                        repository.loadSchedule()
                        coVerify { storage.saveSchedule(scheduleMock) }
                    }
                }

                And("dataSource returns null") {
                    Then("it should throw an error") {
                        coEvery { dataSource.getSchedule() } returns null
                        shouldThrow<IllegalStateException> { repository.loadSchedule() }
                    }
                }
            }

            When("getTasks is called") {
                And("storage has quizId for date and level") {
                    And("dataSource returns quiz") {
                        Then("it should return quiz and preload data") {
                            coEvery { timeManager.getCurrentDate() } returns dateMock
                            every { storage.getQuizId(any(), any()) } returns quizIdMock
                            coEvery { dataSource.getQuiz(any()) } returns quizWithMatchingMock

                            repository.getTasks(levelMock).test {
                                awaitItem() shouldBe ProgressWrapper.Loading(0f)
                                repeat(8) {
                                    awaitItem() shouldBe ProgressWrapper.Loading((it.toFloat() + 1) / 8)
                                }
                                awaitItem() shouldBe ProgressWrapper.Success(quizWithMatchingMock)
                                awaitComplete()
                            }

                            coVerify { imageLoadService.loadImage("image_url") }
                            coVerify { audioLoadService.loadAudio("audio_url") }
                            coVerify { imageLoadService.loadImage("image_l") }
                            coVerify { imageLoadService.loadImage("image_r") }
                            coVerify { audioLoadService.loadAudio("audio_l") }
                            coVerify { audioLoadService.loadAudio("audio_r") }
                        }
                    }

                    And("dataSource returns null") {
                        Then("it should return null") {
                            coEvery { timeManager.getCurrentDate() } returns dateMock
                            coEvery { dataSource.getQuiz(any()) } returns null
                            repository.getTasks(levelMock).test {
                                awaitItem() should beInstanceOf<ProgressWrapper.Failure<QuizDTO>>()
                                awaitComplete()
                            }
                        }
                    }
                }

                And("storage does not have quizId") {
                    Then("it should return null") {
                        coEvery { timeManager.getCurrentDate() } returns dateMock
                        every { storage.getQuizId(any(), any()) } returns null

                        repository.getTasks(levelMock).test {
                            awaitItem() should beInstanceOf<ProgressWrapper.Failure<QuizDTO>>()
                            awaitComplete()
                        }
                    }
                }
            }
        }
    },
)
