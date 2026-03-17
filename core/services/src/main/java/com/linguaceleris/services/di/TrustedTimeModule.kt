package com.linguaceleris.services.di

import android.content.Context
import com.google.android.gms.time.TrustedTime
import com.linguaceleris.services.TrustedTimeClientAccessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrustedTimeModule {

    @Provides
    @Singleton
    fun provideTrustedTimeClientAccessor(
        @ApplicationContext context: Context,
    ): TrustedTimeClientAccessor = object : TrustedTimeClientAccessor {
        override fun createClient() = TrustedTime.createClient(context)
    }
}
