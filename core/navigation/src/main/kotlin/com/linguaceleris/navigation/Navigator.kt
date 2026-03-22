package com.linguaceleris.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class Navigator {

    val backStack: NavBackStack<NavKey> = NavBackStack()

    fun navigateTo(destination: NavKey) {
        backStack.add(destination)
    }

    fun back() {
        backStack.removeLastOrNull()
    }

    fun replace(destination: NavKey) {
        backStack.removeLastOrNull()
        backStack.add(destination)
    }
}
