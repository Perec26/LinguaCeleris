package com.linguaceleris.start.impl

internal data class StartUiState(
    val template: Unit = Unit,
    val number: Int = 0,
) {

    fun increaseNumber(): StartUiState = copy(number = number + 1)
}
