package com.linguaceleris.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance

class Navigator {

    val backStack: NavBackStack<NavKey> = NavBackStack()
    private val _results = MutableSharedFlow<NavResult>(extraBufferCapacity = 1)
    val results: SharedFlow<NavResult> = _results.asSharedFlow()

    inline fun <reified T : NavResult> getResultFlow(): Flow<T> = results.filterIsInstance<T>()

    fun navigateTo(destination: NavKey) {
        backStack.add(destination)
    }

    fun back() {
        backStack.removeLastOrNull()
    }

    fun backTo(destination: NavKey) {
        while (backStack.isNotEmpty() && backStack.last() != destination) {
            backStack.removeLastOrNull()
        }
    }

    suspend fun backWithResult(result: NavResult) {
        backStack.removeLastOrNull()
        _results.emit(result)
    }

    suspend fun backToWithResult(destination: NavKey, result: NavResult) {
        backTo(destination)
        _results.emit(result)
    }

    fun replace(destination: NavKey) {
        backStack.removeLastOrNull()
        backStack.add(destination)
    }

    fun startWith(destination: NavKey) {
        backStack.clear()
        backStack.add(destination)
    }
}
