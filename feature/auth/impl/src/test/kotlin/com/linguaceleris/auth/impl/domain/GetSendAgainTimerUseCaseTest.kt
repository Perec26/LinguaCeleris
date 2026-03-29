package com.linguaceleris.auth.impl.domain

import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetSendAgainTimerUseCaseTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        val useCase = GetSendAgainTimerUseCase()

        Given("GetSendAgainTimerUseCase") {
            When("invoked") {
                Then("it should emit values from 60 down to 0 every second") {
                    useCase().test {
                        awaitItem() shouldBe 60
                        testDispatcher.scheduler.advanceTimeBy(1000)
                        awaitItem() shouldBe 59
                        testDispatcher.scheduler.advanceTimeBy(58000)
                        awaitItem() shouldBe 1
                        testDispatcher.scheduler.advanceTimeBy(1000)
                        awaitItem() shouldBe 0
                        awaitComplete()
                    }
                }
            }
        }
    },
)
