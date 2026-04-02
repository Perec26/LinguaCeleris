package com.linguaceleris.settings.impl.domain

import com.linguaceleris.settings.SettingsRepository
import javax.inject.Inject

internal class UpdateUseDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend operator fun invoke(useDarkTheme: Boolean) = repository.updateUseDarkTheme(useDarkTheme)
}
