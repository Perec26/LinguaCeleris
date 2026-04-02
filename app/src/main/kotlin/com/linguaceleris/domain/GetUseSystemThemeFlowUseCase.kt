package com.linguaceleris.domain

import com.linguaceleris.settings.SettingsRepository
import javax.inject.Inject

internal class GetUseSystemThemeFlowUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    operator fun invoke() = repository.getUseSystemThemeFlow()
}
