package com.linguaceleris.start.impl

internal sealed class StartEvent {
    object OnButtonClick : StartEvent()
    object OnButton2Click : StartEvent()
}
