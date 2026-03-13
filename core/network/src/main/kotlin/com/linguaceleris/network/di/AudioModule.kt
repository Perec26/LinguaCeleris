package com.linguaceleris.network.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

private const val CACHE_SIZE_MB = 16L

@OptIn(UnstableApi::class)
@Module
@InstallIn(SingletonComponent::class)
class AudioModule {

    @Provides
    @Singleton
    fun provideAudioCache(@ApplicationContext context: Context): Cache = SimpleCache(
        File(context.cacheDir, "audio_preloads"),
        LeastRecentlyUsedCacheEvictor(CACHE_SIZE_MB * 1024 * 1024),
        StandaloneDatabaseProvider(context),
    )

    @Provides
    @Singleton
    fun provideCacheDataSourceFactory(cache: Cache): CacheDataSource.Factory =
        CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(DefaultHttpDataSource.Factory())
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
}
