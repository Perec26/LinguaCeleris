package com.linguaceleris.media

import android.content.ContentResolver
import android.net.Uri
import androidx.annotation.RawRes
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class PlayerManager @Inject constructor(
    private val player: ExoPlayer,
) {

    fun playRaw(@RawRes rawId: Int) {
        val uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .path(rawId.toString())
            .build()
        MediaItem.fromUri(uri).play()
    }

    fun playUrl(url: String) = MediaItem.fromUri(url).play()

    fun stop() = player.stop()

    fun release() = player.release()

    private fun MediaItem.play() {
        player.setMediaItem(this)
        player.prepare()
        player.play()
    }
}
