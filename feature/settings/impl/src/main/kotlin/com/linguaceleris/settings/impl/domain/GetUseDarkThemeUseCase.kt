package com.linguaceleris.settings.impl.domain

import com.linguaceleris.settings.SettingsRepository
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject

@ExcludeFromKover
internal class GetUseDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend operator fun invoke() = repository.getUseDarkTheme()
}
