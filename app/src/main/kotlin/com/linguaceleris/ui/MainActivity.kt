package com.linguaceleris.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.linguaceleris.designsystem.R
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.PatternSurface
import com.linguaceleris.navigation.LCApp
import com.linguaceleris.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        viewModel
        enableEdgeToEdge()
        setContent {
            val state = viewModel.state.collectAsState().value
            LinguaCelerisTheme(
                useSystemTheme = state.useSystemTheme,
                useDarkTheme = state.useDarkTheme,
            ) {
                PatternSurface(
                    modifier = Modifier.fillMaxSize(),
                    patternRes = R.drawable.seamless_background,
                ) {
                    LCApp(navigator)
                }
            }
        }
    }
}
