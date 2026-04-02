package com.linguaceleris.settings.impl.domain

import com.linguaceleris.settings.SettingsRepository
import javax.inject.Inject

internal class GetUseDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend operator fun invoke() = repository.getUseDarkTheme()
}
