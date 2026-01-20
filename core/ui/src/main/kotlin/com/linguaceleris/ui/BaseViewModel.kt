package com.linguaceleris.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : Any, EVENT : Any, NAVIGATION_EVENT : Any>(
    initialState: STATE,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NAVIGATION_EVENT>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    val viewState: STATE
        get() = state.value

    abstract fun onEvent(event: EVENT)

    protected fun updateState(block: STATE.() -> STATE) {
        _state.value = block.invoke(_state.value)
    }

    fun launch(block: suspend CoroutineScope.() -> Unit): Job = viewModelScope.launch {
        block.invoke(this)
    }

    protected fun onNavigationEvent(event: NAVIGATION_EVENT) {
        launch { _navigationEvent.emit(event) }
    }
}