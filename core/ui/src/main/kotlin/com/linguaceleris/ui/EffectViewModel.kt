package com.linguaceleris.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class EffectViewModel<STATE : Any, EVENT : Any, EFFECT : Any>(initialState: STATE) :
    BaseViewModel<STATE, EVENT>(initialState) {

    private val _effect = MutableSharedFlow<EFFECT>()
    val effect = _effect.asSharedFlow()

    protected fun sendEffect(effect: EFFECT) {
        launch { _effect.emit(effect) }
    }
}
