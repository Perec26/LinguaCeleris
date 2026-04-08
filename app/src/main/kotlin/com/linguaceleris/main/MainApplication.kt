package com.linguaceleris.main

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
internal class MainApplication :
    Application(),
    SingletonImageLoader.Factory {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun newImageLoader(context: PlatformContext) = imageLoader

    override fun attachBaseContext(base: Context) {
        val locale = Locale.forLanguageTag("ru")
        val config = Configuration(base.resources.configuration).apply {
            setLocale(locale)
        }
        super.attachBaseContext(base.createConfigurationContext(config))
    }
}
