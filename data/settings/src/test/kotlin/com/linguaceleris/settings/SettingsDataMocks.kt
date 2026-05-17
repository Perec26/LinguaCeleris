package com.linguaceleris.settings

import com.linguaceleris.services.preferences.DataStoreService
import io.mockk.mockk

internal object SettingsDataMocks {
    val dataStoreService = mockk<DataStoreService>(relaxed = true)
}
