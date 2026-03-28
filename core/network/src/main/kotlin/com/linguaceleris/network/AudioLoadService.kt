package com.linguaceleris.network

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheWriter
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(UnstableApi::class)
class AudioLoadService @Inject constructor(private val cacheFactory: CacheDataSource.Factory) {

    suspend fun loadAudio(url: String) {
        withContext(Dispatchers.IO) {
            CacheWriter(
                cacheFactory.createDataSource(),
                DataSpec(url.toUri()),
                null,
                null,
            ).cache()
        }
    }
}
