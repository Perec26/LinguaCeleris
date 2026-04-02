package com.linguaceleris.settings.impl.domain

import com.linguaceleris.settings.SettingsRepository
import javax.inject.Inject

internal class UpdateUseSystemThemeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend operator fun invoke(useSystemTheme: Boolean) =
        repository.updateUseSystemTheme(useSystemTheme)
}
