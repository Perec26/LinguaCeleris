package com.linguaceleris.settings

import com.linguaceleris.settings.SettingsDataMocks.dataStoreService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class SettingsRepositoryTest : BehaviorSpec(
    {
        val testDispatcher = StandardTestDispatcher()
        lateinit var repository: SettingsRepository

        fun createRepository() = SettingsRepository(
            dataStoreService = dataStoreService,
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

        Given("SettingsRepository") {

            When("getUseSystemThemeFlow is called") {
                Then("it should return flow from dataStoreService") {
                    val expectedFlow = flowOf(true)
                    every { dataStoreService.getUseSystemThemeFlow() } returns expectedFlow

                    val result = repository.getUseSystemThemeFlow()

                    result shouldBe expectedFlow
                }
            }

            When("getUseDarkThemeFlow is called") {
                Then("it should return flow from dataStoreService") {
                    val expectedFlow = flowOf(false)
                    every { dataStoreService.getUseDarkThemeFlow() } returns expectedFlow

                    val result = repository.getUseDarkThemeFlow()

                    result shouldBe expectedFlow
                }
            }

            When("getUseSystemTheme is called") {
                Then("it should return the first value from the flow") {
                    every { dataStoreService.getUseSystemThemeFlow() } returns flowOf(true)

                    val result = repository.getUseSystemTheme()

                    result shouldBe true
                }
            }

            When("getUseDarkTheme is called") {
                Then("it should return the first value from the flow") {
                    every { dataStoreService.getUseDarkThemeFlow() } returns flowOf(false)

                    val result = repository.getUseDarkTheme()

                    result shouldBe false
                }
            }

            When("updateUseSystemTheme is called") {
                Then("it should update value in dataStoreService") {
                    repository.updateUseSystemTheme(true)

                    coVerify { dataStoreService.updateUseSystemTheme(true) }
                }
            }

            When("updateUseDarkTheme is called") {
                Then("it should update value in dataStoreService") {
                    repository.updateUseDarkTheme(false)

                    coVerify { dataStoreService.updateUseDarkTheme(false) }
                }
            }
        }
    },
)
