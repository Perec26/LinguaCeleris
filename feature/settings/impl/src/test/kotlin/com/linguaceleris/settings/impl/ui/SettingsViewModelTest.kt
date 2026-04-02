package com.linguaceleris.settings.impl.ui

import com.linguaceleris.settings.impl.ui.SettingsMocks.getUseDarkThemeUseCase
import com.linguaceleris.settings.impl.ui.SettingsMocks.getUseSystemThemeUseCase
import com.linguaceleris.settings.impl.ui.SettingsMocks.navigator
import com.linguaceleris.settings.impl.ui.SettingsMocks.updateUseDarkThemeUseCase
import com.linguaceleris.settings.impl.ui.SettingsMocks.updateUseSystemThemeUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class SettingsViewModelTest : BehaviorSpec(
    {

        val testDispatcher = StandardTestDispatcher()
        lateinit var viewModel: SettingsViewModel

        beforeSpec { Dispatchers.setMain(testDispatcher) }
        afterSpec { Dispatchers.resetMain() }

        beforeEach {
            clearAllMocks()
            coEvery { getUseSystemThemeUseCase() } returns true
            coEvery { getUseDarkThemeUseCase() } returns true

            viewModel = SettingsViewModel(
                navigator = navigator,
                getUseSystemThemeUseCase = getUseSystemThemeUseCase,
                getUseDarkThemeUseCase = getUseDarkThemeUseCase,
                updateUseSystemThemeUseCase = updateUseSystemThemeUseCase,
                updateUseDarkThemeUseCase = updateUseDarkThemeUseCase,
            )
        }

        Given("SettingsViewModel") {

            When("initialized") {
                Then("it should load settings values") {
                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.state.value.isLoading shouldBe false
                    viewModel.state.value.useSystemTheme shouldBe true
                    viewModel.state.value.useDarkTheme shouldBe true
                    coVerify { getUseSystemThemeUseCase() }
                    coVerify { getUseDarkThemeUseCase() }
                }
            }

            When("OnBackPressed is received") {
                Then("it should navigate back") {
                    viewModel.onEvent(SettingsEvent.OnBackPressed)
                    verify { navigator.back() }
                }
            }

            When("OnUseSystemThemeClick is received") {
                Then("it should toggle system theme setting") {
                    val initialValue = viewModel.state.value.useSystemTheme

                    viewModel.onEvent(SettingsEvent.OnUseSystemThemeClick)
                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.state.value.useSystemTheme shouldBe !initialValue
                    coVerify { updateUseSystemThemeUseCase(!initialValue) }
                }
            }

            When("OnUseDarkThemeClick is received") {
                Then("it should toggle dark theme setting") {
                    val initialValue = viewModel.state.value.useDarkTheme

                    viewModel.onEvent(SettingsEvent.OnUseDarkThemeClick)
                    testDispatcher.scheduler.advanceUntilIdle()

                    viewModel.state.value.useDarkTheme shouldBe !initialValue
                    coVerify { updateUseDarkThemeUseCase(!initialValue) }
                }
            }
        }
    },
)
