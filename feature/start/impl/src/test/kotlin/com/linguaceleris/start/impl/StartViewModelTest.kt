package com.linguaceleris.start.impl

import com.linguaceleris.auth.AuthState
import com.linguaceleris.auth.api.startWithEmailVerification
import com.linguaceleris.auth.api.startWithSignIn
import com.linguaceleris.home.api.startWithHome
import com.linguaceleris.start.impl.StartMocks.getAuthStateUseCase
import com.linguaceleris.start.impl.StartMocks.navigator
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
internal class StartViewModelTest : BehaviorSpec(
    {
        val testDispatcher = StandardTestDispatcher()

        fun createViewModel() = StartViewModel(
            navigator = navigator,
            getAuthStateUseCase = getAuthStateUseCase,
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
        }

        Given("StartViewModel") {
            When("initialized") {
                And("auth state is NOT_LOGGED_IN") {
                    Then("it should navigate to sign in") {
                        every { getAuthStateUseCase() } returns AuthState.NOT_LOGGED_IN
                        createViewModel()
                        verify { navigator.startWithSignIn() }
                    }
                }

                And("auth state is EMAIL_NOT_VERIFIED") {
                    Then("it should navigate to email verification") {
                        every { getAuthStateUseCase() } returns AuthState.EMAIL_NOT_VERIFIED
                        createViewModel()
                        verify { navigator.startWithEmailVerification() }
                    }
                }

                And("auth state is LOGGED_IN") {
                    Then("it should navigate to home") {
                        every { getAuthStateUseCase() } returns AuthState.LOGGED_IN
                        createViewModel()
                        verify { navigator.startWithHome() }
                    }
                }

                And("auth state is ANONYMOUS") {
                    Then("it should navigate to home") {
                        every { getAuthStateUseCase() } returns AuthState.ANONYMOUS
                        createViewModel()
                        verify { navigator.startWithHome() }
                    }
                }
            }
        }
    },
)
