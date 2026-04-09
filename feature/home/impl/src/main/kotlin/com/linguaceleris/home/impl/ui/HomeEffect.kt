package com.linguaceleris.home.impl.ui

import com.linguaceleris.home.impl.R
import com.linguaceleris.ui.utils.UiText

internal sealed class HomeEffect {
    data class OpenYoutube(val link: UiText = UiText.StringResource(R.string.home_url_youtube)) :
        HomeEffect()

    data class OpenTelegram(val link: UiText = UiText.StringResource(R.string.home_url_telegram)) :
        HomeEffect()
}
