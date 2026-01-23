package com.linguaceleris.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.navigation.LCApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinguaCelerisTheme {
                LCApp()
            }
        }
    }
}