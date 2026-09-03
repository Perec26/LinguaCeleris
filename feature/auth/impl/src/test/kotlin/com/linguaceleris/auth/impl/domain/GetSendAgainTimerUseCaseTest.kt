package com.linguaceleris.auth.impl.domain

import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetSendAgainTimerUseCaseTest : BehaviorSpec(
    {
        val useCase = GetSendAgainTimerUseCase()

        Given("GetSendAgainTimerUseCase") {

            When("flow is collected") {

                Then("should emit values from 60 down to 0 and complete") {
                    runTest {
                        useCase().test {
                            for (expected in 60 downTo 0) {
                                awaitItem() shouldBe expected
                            }
                            awaitComplete()
                        }
                    }
                }

                Then("should emit next value only after 1 second delay") {
                    runTest {
                        useCase().test {
                            awaitItem() shouldBe 60
                            expectNoEvents()

                            advanceTimeBy(1.seconds)
                            awaitItem() shouldBe 59

                            cancelAndIgnoreRemainingEvents()
                        }
                    }
                }
            }
        }
    },
)
