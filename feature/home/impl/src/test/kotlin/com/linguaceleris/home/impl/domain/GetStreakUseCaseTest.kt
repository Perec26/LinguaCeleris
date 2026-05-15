package com.linguaceleris.home.impl.domain

import com.linguaceleris.home.impl.domain.HomeDomainMocks.authRepository
import com.linguaceleris.home.impl.domain.HomeDomainMocks.streakRepository
import com.linguaceleris.home.impl.domain.HomeDomainMocks.timeManager
import com.linguaceleris.home.impl.domain.mapper.toUi
import com.linguaceleris.home.impl.domain.model.UserNotFoundException
import com.linguaceleris.streak.model.StreakDTO
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import kotlinx.datetime.LocalDate

internal class GetStreakUseCaseTest : BehaviorSpec({
    val useCase = GetStreakUseCase(timeManager, authRepository, streakRepository)

    beforeEach {
        clearAllMocks()
    }

    Given("GetStreakUseCase") {
        val userId = "test_user"
        val currentDate = LocalDate(2025, 1, 10)
        val streakDto = StreakDTO(current = 5, longest = 10)

        When("user is logged in") {
            Then("it should return mapped StreakUI") {
                every { authRepository.getCurrentUserId() } returns userId
                coEvery { timeManager.getCurrentDate() } returns currentDate
                coEvery { streakRepository.getStreak(userId) } returns streakDto

                val expected = streakDto.toUi(currentDate)
                useCase() shouldBe expected
            }
        }

        When("user is not logged in") {
            Then("it should throw error") {
                every { authRepository.getCurrentUserId() } returns null

                shouldThrow<UserNotFoundException> { useCase() }
            }
        }
    }
})
