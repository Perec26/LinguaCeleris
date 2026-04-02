package com.linguaceleris.domain

import com.linguaceleris.settings.SettingsRepository
import javax.inject.Inject

internal class GetUseDarkThemeFlowUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    operator fun invoke() = repository.getUseDarkThemeFlow()
}
