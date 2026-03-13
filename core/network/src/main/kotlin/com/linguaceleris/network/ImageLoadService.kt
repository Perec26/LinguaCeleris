package com.linguaceleris.network

import android.content.Context
import coil3.ImageLoader
import coil3.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

class ImageLoadService @Inject constructor(
    private val imageLoader: ImageLoader,
    @param:ApplicationContext private val context: Context,
) {

    suspend fun loadImage(url: String) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        imageLoader.execute(request)
    }
}
