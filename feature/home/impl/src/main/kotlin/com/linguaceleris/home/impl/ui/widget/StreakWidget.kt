package com.linguaceleris.home.impl.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.Gradients.toLightBaseGradient
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.model.StreakUI

@Composable
internal fun StreakWidget(streak: StreakUI) {
    when (streak) {
        is StreakUI.Dead -> DeadStreak(streak)
        is StreakUI.Freeze -> FreezeStreak(streak)
        StreakUI.NeverStarted -> NeverStartedStreak()
        is StreakUI.TodayCompleted -> TodayCompletedStreak(streak)
        is StreakUI.TodayNotCompleted -> TodayNotCompletedStreak(streak)
    }
}

@Composable
private fun DeadStreak(streak: StreakUI.Dead) {
    StreakBase(
        backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
        icon = com.linguaceleris.designsystem.R.drawable.fire_dead,
        title = stringResource(R.string.home_streak_dead_title),
        subtitle = stringResource(
            R.string.home_streak_dead_subtitle,
            streak.longestStreak,
        ),
    )
}

@Composable
private fun FreezeStreak(streak: StreakUI.Freeze) {
    StreakBase(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        icon = com.linguaceleris.designsystem.R.drawable.fire_freeze,
        title = stringResource(streak.title),
        subtitle = stringResource(streak.subtitle),
    )
}

@Composable
private fun NeverStartedStreak() {
    StreakBase(
        backgroundColor = MaterialTheme.extendedColors.green.colorContainer,
        icon = com.linguaceleris.designsystem.R.drawable.leaf,
        title = stringResource(R.string.home_streak_never_started_title),
        subtitle = stringResource(R.string.home_streak_never_started_subtitle_),
    )
}

@Composable
private fun TodayCompletedStreak(streak: StreakUI.TodayCompleted) {
    StreakBase(
        backgroundColor = MaterialTheme.extendedColors.yellow.colorContainer,
        icon = com.linguaceleris.designsystem.R.drawable.fire_active,
        title = pluralStringResource(
            R.plurals.home_streak_completed_title,
            streak.streak,
            streak.streak,
        ),
        subtitle = stringResource(streak.subtitle),
    )
}

@Composable
private fun TodayNotCompletedStreak(streak: StreakUI.TodayNotCompleted) {
    StreakBase(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        icon = com.linguaceleris.designsystem.R.drawable.fire_not_active,
        title = stringResource(R.string.home_streak_today_not_completed_title),
        subtitle = pluralStringResource(
            R.plurals.home_streak_today_not_completed_subtitle,
            streak.streak,
            streak.streak,
        ),
    )
}

@Composable
private fun StreakBase(
    backgroundColor: Color,
    @DrawableRes icon: Int,
    title: String,
    subtitle: String,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor.toLightBaseGradient()),
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Image(
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight(),
                    painter = painterResource(icon),
                    contentDescription = null,
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        color = contentColorFor(backgroundColor),
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Text(
                        color = contentColorFor(backgroundColor),
                        text = subtitle,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun StreakWidgetPreview() {
    LCPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StreakWidget(StreakUI.Dead(150))
            StreakWidget(StreakUI.Freeze.OneFreeze)
            StreakWidget(StreakUI.Freeze.NoneFreeze)
            StreakWidget(StreakUI.NeverStarted)
            StreakWidget(StreakUI.TodayCompleted(1, subtitle = R.string.home_streak_completed_any3))
            StreakWidget(StreakUI.TodayNotCompleted(2))
        }
    }
}
