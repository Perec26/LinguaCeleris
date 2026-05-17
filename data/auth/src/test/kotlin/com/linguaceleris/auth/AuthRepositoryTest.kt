package com.linguaceleris.auth

import com.linguaceleris.auth.AuthDataMocks.credentialService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify

class AuthRepositoryTest : BehaviorSpec({
    val repository = AuthRepository(credentialService)

    afterContainer {
        clearAllMocks()
    }

    Given("AuthRepository") {
        When("signInWithGoogle is called") {
            val idToken = "test_token"
            coEvery { credentialService.signInWithGoogle(idToken) } returns true

            Then("it should call credentialService.signInWithGoogle") {
                repository.signInWithGoogle(idToken) shouldBe true
                coVerify { credentialService.signInWithGoogle(idToken) }
            }
        }

        When("linkWithGoogle is called") {
            val idToken = "test_token"
            coEvery { credentialService.linkWithGoogle(idToken) } returns true

            Then("it should call credentialService.linkWithGoogle") {
                repository.linkWithGoogle(idToken) shouldBe true
                coVerify { credentialService.linkWithGoogle(idToken) }
            }
        }

        When("signOut is called") {
            Then("it should call credentialService.signOut") {
                repository.signOut()
                verify { credentialService.signOut() }
            }
        }

        When("register is called") {
            val nickname = "test_user"
            val email = "test@example.com"
            val password = "password"

            Then("it should call register, updateDisplayName and sendEmailVerification") {
                repository.register(nickname, email, password)
                coVerify {
                    credentialService.register(email, password)
                    credentialService.updateDisplayName(nickname)
                    credentialService.sendEmailVerification()
                }
            }
        }

        When("sendEmailVerification is called") {
            Then("it should call credentialService.sendEmailVerification") {
                repository.sendEmailVerification()
                coVerify { credentialService.sendEmailVerification() }
            }
        }

        When("getEmailVerification is called") {
            coEvery { credentialService.getEmailVerification() } returns true

            Then("it should reload user and return verification status") {
                repository.getEmailVerification() shouldBe true
                coVerify {
                    credentialService.reloadUser()
                    credentialService.getEmailVerification()
                }
            }
        }

        When("getAuthState is called") {
            And("user is not logged in") {
                every { credentialService.isLoggedIn() } returns false
                Then("it should return NOT_LOGGED_IN") {
                    repository.getAuthState() shouldBe AuthState.NOT_LOGGED_IN
                }
            }

            And("user is anonymous") {
                every { credentialService.isLoggedIn() } returns true
                every { credentialService.isAnonymous() } returns true
                Then("it should return ANONYMOUS") {
                    repository.getAuthState() shouldBe AuthState.ANONYMOUS
                }
            }

            And("user is logged in but email not verified") {
                every { credentialService.isLoggedIn() } returns true
                every { credentialService.isAnonymous() } returns false
                every { credentialService.getEmailVerification() } returns false
                Then("it should return EMAIL_NOT_VERIFIED") {
                    repository.getAuthState() shouldBe AuthState.EMAIL_NOT_VERIFIED
                }
            }

            And("user is fully logged in") {
                every { credentialService.isLoggedIn() } returns true
                every { credentialService.isAnonymous() } returns false
                every { credentialService.getEmailVerification() } returns true
                Then("it should return LOGGED_IN") {
                    repository.getAuthState() shouldBe AuthState.LOGGED_IN
                }
            }
        }

        When("isGuest is called") {
            every { credentialService.isAnonymous() } returns true
            Then("it should return true") {
                repository.isGuest() shouldBe true
            }
        }

        When("getWebClientId is called") {
            every { credentialService.getWebClientId() } returns "web_id"
            Then("it should return web client id") {
                repository.getWebClientId() shouldBe "web_id"
            }
        }

        When("signInAnonymously is called") {
            Then("it should call credentialService.signInAnonymously") {
                repository.signInAnonymously()
                coVerify { credentialService.signInAnonymously() }
            }
        }

        When("getCurrentUserId is called") {
            every { credentialService.getCurrentUserId() } returns "uid"
            Then("it should return current user id") {
                repository.getCurrentUserId() shouldBe "uid"
            }
        }

        When("signInWithEmail is called") {
            val email = "test@example.com"
            val password = "password"
            Then("it should call credentialService.signInWithEmail") {
                repository.signInWithEmail(email, password)
                coVerify { credentialService.signInWithEmail(email, password) }
            }
        }

        When("resetPassword is called") {
            val email = "test@example.com"
            Then("it should call credentialService.resetPassword") {
                repository.resetPassword(email)
                coVerify { credentialService.resetPassword(email) }
            }
        }
    }
})
