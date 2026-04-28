package com.linguaceleris.home.impl.domain

import app.cash.turbine.test
import com.linguaceleris.home.impl.domain.HomeDomainMocks.timeManager
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetNextDayUseCaseTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        val useCase = GetNextDayUseCase(timeManager)

        beforeSpec {
            Dispatchers.setMain(testDispatcher)
        }

        beforeEach {
            clearAllMocks()
        }

        afterSpec {
            Dispatchers.resetMain()
            unmockkAll()
        }

        Given("GetNextDayUseCase") {
            When("collecting the flow") {
                Then("it should emit durations returned by timeManager and delay") {
                    coEvery { timeManager.getTimeTillMidnight() } returns 5.hours andThen
                        4.hours + 59.minutes + 58.seconds

                    useCase().test {
                        awaitItem() shouldBe 5.hours
                        testDispatcher.scheduler.advanceTimeBy(1001)
                        awaitItem() shouldBe 4.hours + 59.minutes + 58.seconds
                        cancelAndIgnoreRemainingEvents()
                    }
                }
            }
        }
    },
)
