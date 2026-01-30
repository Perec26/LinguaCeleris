package com.linguaceleris.login.impl

internal sealed class LoginEvent {
    data object SomeThing : LoginEvent()
}
