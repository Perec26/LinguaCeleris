package com.linguaceleris.ui.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {

    data class StringResource(val resId: Int, val args: List<UiTextArg> = emptyList()) : UiText()

    data class DynamicString(val value: String) : UiText()

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(resId, *args.map { it.resolve() }.toTypedArray())
    }

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value

        is StringResource -> context.getString(
            resId,
            *args.map { it.resolve(context) }.toTypedArray(),
        )
    }
}

sealed class UiTextArg {
    data class StringArg(val value: String) : UiTextArg()
    data class IntArg(val value: Int) : UiTextArg()
    data class FloatArg(val value: Float) : UiTextArg()

    @Composable
    fun resolve(): Any = when (this) {
        is StringArg -> value
        is IntArg -> value
        is FloatArg -> value
    }

    fun resolve(context: Context): Any = when (this) {
        is StringArg -> value
        is IntArg -> value
        is FloatArg -> value
    }
}
