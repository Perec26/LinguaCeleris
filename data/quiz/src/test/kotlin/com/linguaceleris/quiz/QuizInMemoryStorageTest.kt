package com.linguaceleris.quiz

import com.linguaceleris.quiz.QuizDataMocks.dateMock
import com.linguaceleris.quiz.QuizDataMocks.fullScheduleMock
import com.linguaceleris.quiz.model.QuizLevelDTO
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate

internal class QuizInMemoryStorageTest : BehaviorSpec({
    val storage = QuizInMemoryStorage()

    Given("QuizInMemoryStorage") {

        When("schedule is NOT saved") {
            Then("getQuizId should return null") {
                storage.getQuizId(dateMock, QuizLevelDTO.BASIC) shouldBe null
            }
        }

        When("schedule IS saved") {
            storage.saveSchedule(fullScheduleMock)

            Then("it should return correct quiz id for BASIC level") {
                storage.getQuizId(dateMock, QuizLevelDTO.BASIC) shouldBe "basic_id"
            }

            Then("it should return correct quiz id for INTERMEDIATE level") {
                storage.getQuizId(dateMock, QuizLevelDTO.INTERMEDIATE) shouldBe "inter_id"
            }

            Then("it should return correct quiz id for ADVANCED level") {
                storage.getQuizId(dateMock, QuizLevelDTO.ADVANCED) shouldBe "adv_id"
            }

            Then("it should return null for non-existing date") {
                storage.getQuizId(LocalDate(2024, 5, 17), QuizLevelDTO.BASIC) shouldBe null
            }
        }
    }
})
