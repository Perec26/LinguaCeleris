package com.linguaceleris.lib

sealed class ProgressWrapper<T> {
    data class Success<T>(val value: T) : ProgressWrapper<T>()
    data class Failure<T>(val error: Throwable) : ProgressWrapper<T>()
    data class Loading<T>(val progress: Float) : ProgressWrapper<T>()
}
