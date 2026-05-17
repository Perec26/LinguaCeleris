package com.linguaceleris.streak

import com.linguaceleris.streak.StreakDataMocks.dataSource
import com.linguaceleris.streak.StreakDataMocks.fourDaysAgo
import com.linguaceleris.streak.StreakDataMocks.threeDaysAgo
import com.linguaceleris.streak.StreakDataMocks.timeManager
import com.linguaceleris.streak.StreakDataMocks.today
import com.linguaceleris.streak.StreakDataMocks.yesterday
import com.linguaceleris.streak.model.StreakDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify

internal class StreakRepositoryTest : BehaviorSpec(
    {
        val repository = StreakRepository(timeManager, dataSource)
        val userId = "user-123"

        afterEach { clearAllMocks() }

        Given("StreakRepository") {

            When("getStreak - user has no streak") {
                coEvery { dataSource.getStreak(userId) } returns null

                Then("returns empty StreakDTO") {
                    repository.getStreak(userId) shouldBe StreakDTO()
                }
            }

            When("getStreak - user has streak") {
                val mockStreak = StreakDTO(current = 5, longest = 10)
                coEvery { dataSource.getStreak(userId) } returns mockStreak

                Then("returns user streak") {
                    repository.getStreak(userId) shouldBe mockStreak
                }
            }

            When("updateBasicStreak - first time") {
                coEvery { dataSource.getStreak(userId) } returns null
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateBasicStreak(userId)

                Then("initializes streak with 1") {
                    coVerify {
                        dataSource.updateStreak(
                            userId = userId,
                            streak = StreakDTO(current = 1, longest = 1, basicLastCompletedDate = today),
                        )
                    }
                }
            }

            When("updateBasicStreak - same day") {
                val existingStreak = StreakDTO(
                    current = 5,
                    longest = 10,
                    intermediateLastCompletedDate = today,
                )
                coEvery { dataSource.getStreak(userId) } returns existingStreak
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateBasicStreak(userId)

                Then("does not increment current streak but updates level date") {
                    coVerify {
                        dataSource.updateStreak(
                            userId = userId,
                            streak = existingStreak.copy(basicLastCompletedDate = today),
                        )
                    }
                }
            }

            When("updateBasicStreak - consecutive day") {
                val existingStreak = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = yesterday,
                )
                coEvery { dataSource.getStreak(userId) } returns existingStreak
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateBasicStreak(userId)

                Then("increments current streak") {
                    coVerify {
                        dataSource.updateStreak(
                            userId,
                            StreakDTO(
                                current = 6,
                                longest = 10,
                                basicLastCompletedDate = today,
                            ),
                        )
                    }
                }
            }

            When("updateBasicStreak - within MAX_DAYS") {
                val existingStreak = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = threeDaysAgo,
                )
                coEvery { dataSource.getStreak(userId) } returns existingStreak
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateBasicStreak(userId)

                Then("increments current streak") {
                    coVerify {
                        dataSource.updateStreak(
                            userId,
                            StreakDTO(
                                current = 6,
                                longest = 10,
                                basicLastCompletedDate = today,
                            ),
                        )
                    }
                }
            }

            When("updateBasicStreak - after MAX_DAYS") {
                val existingStreak = StreakDTO(
                    current = 5,
                    longest = 10,
                    basicLastCompletedDate = fourDaysAgo,
                )
                coEvery { dataSource.getStreak(userId) } returns existingStreak
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateBasicStreak(userId)

                Then("resets current streak to 1") {
                    coVerify {
                        dataSource.updateStreak(
                            userId,
                            StreakDTO(
                                current = 1,
                                longest = 10,
                                basicLastCompletedDate = today,
                            ),
                        )
                    }
                }
            }

            When("updateBasicStreak - new longest streak") {
                val existingStreak = StreakDTO(
                    current = 10,
                    longest = 10,
                    basicLastCompletedDate = yesterday,
                )
                coEvery { dataSource.getStreak(userId) } returns existingStreak
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateBasicStreak(userId)

                Then("updates longest streak") {
                    coVerify {
                        dataSource.updateStreak(
                            userId,
                            StreakDTO(
                                current = 11,
                                longest = 11,
                                basicLastCompletedDate = today,
                            ),
                        )
                    }
                }
            }

            When("updateIntermediateStreak - called") {
                coEvery { dataSource.getStreak(userId) } returns null
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateIntermediateStreak(userId)

                Then("updates intermediate date") {
                    coVerify {
                        dataSource.updateStreak(
                            userId,
                            match { it.intermediateLastCompletedDate == today },
                        )
                    }
                }
            }

            When("updateAdvancedStreak - called") {
                coEvery { dataSource.getStreak(userId) } returns null
                coEvery { timeManager.getCurrentDate() } returns today
                coEvery { dataSource.updateStreak(userId, any()) } returns Unit

                repository.updateAdvancedStreak(userId)

                Then("updates advanced date") {
                    coVerify {
                        dataSource.updateStreak(
                            userId,
                            match { it.advancedLastCompletedDate == today },
                        )
                    }
                }
            }
        }
    },
)
