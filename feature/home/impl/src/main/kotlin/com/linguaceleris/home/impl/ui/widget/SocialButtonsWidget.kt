@file:PendingUiTests

package com.linguaceleris.home.impl.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.widgets.HapticElement
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.HomeEvent
import com.linguaceleris.testing.PendingUiTests

@Composable
internal fun SocialButtonsWidget(onEvent: (HomeEvent) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SocialButton(
            icon = painterResource(R.drawable.home_youtube_logo),
            onClick = { onEvent(HomeEvent.OnYoutubeClick) },
        )
        SocialButton(
            icon = painterResource(R.drawable.home_telegram_logo),
            onClick = { onEvent(HomeEvent.OnTelegramClick) },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SocialButton(icon: Painter, onClick: () -> Unit) {
    HapticElement { haptic ->
        IconButton(
            modifier = Modifier.size(IconButtonDefaults.mediumContainerSize()),
            shape = IconButtonDefaults.mediumRoundShape,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                onClick.invoke()
            },
            content = {
                Image(
                    modifier = Modifier.size(IconButtonDefaults.largeIconSize),
                    painter = icon,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outlineVariant),
                    contentDescription = null,
                )
            },
        )
    }
}

@PreviewLightDark
@Composable
private fun SocialButtonsWidgetPreview() {
    LCPreview {
        SocialButtonsWidget {}
    }
}
