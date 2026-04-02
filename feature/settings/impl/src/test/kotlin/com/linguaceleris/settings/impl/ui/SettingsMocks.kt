package com.linguaceleris.settings.impl.ui

import com.linguaceleris.navigation.Navigator
import com.linguaceleris.settings.impl.domain.GetUseDarkThemeUseCase
import com.linguaceleris.settings.impl.domain.GetUseSystemThemeUseCase
import com.linguaceleris.settings.impl.domain.UpdateUseDarkThemeUseCase
import com.linguaceleris.settings.impl.domain.UpdateUseSystemThemeUseCase
import io.mockk.mockk

internal object SettingsMocks {
    val navigator = mockk<Navigator>(relaxed = true)
    val getUseSystemThemeUseCase = mockk<GetUseSystemThemeUseCase>(relaxed = true)
    val getUseDarkThemeUseCase = mockk<GetUseDarkThemeUseCase>(relaxed = true)
    val updateUseSystemThemeUseCase = mockk<UpdateUseSystemThemeUseCase>(relaxed = true)
    val updateUseDarkThemeUseCase = mockk<UpdateUseDarkThemeUseCase>(relaxed = true)
}
