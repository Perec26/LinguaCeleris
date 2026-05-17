package com.linguaceleris.auth

import com.linguaceleris.network.CredentialService
import io.mockk.mockk

internal object AuthDataMocks {
    val credentialService = mockk<CredentialService>(relaxed = true)
}
