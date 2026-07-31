@file:PendingUiTests

package com.linguaceleris.home.impl.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import com.linguaceleris.designsystem.theme.extendedColors
import com.linguaceleris.designsystem.widgets.Gradients.toLightBaseGradient
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.SmallScreenPreview
import com.linguaceleris.home.impl.R
import com.linguaceleris.home.impl.ui.model.StreakUI
import com.linguaceleris.testing.PendingUiTests

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
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
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
        icon = com.linguaceleris.designsystem.R.drawable.fire_inactive,
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
                .heightIn(max = 100.dp)
                .background(backgroundColor.toLightBaseGradient()),
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Image(
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(48.dp)
                        .fillMaxHeight(),
                    painter = painterResource(icon),
                    contentDescription = null,
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val color = contentColorFor(backgroundColor)

                    BasicText(
                        color = { color },
                        text = title,
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 2.sp,
                            maxFontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        ),
                    )

                    BasicText(
                        color = { color },
                        text = subtitle,
                        maxLines = 2,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 2.sp,
                            maxFontSize = MaterialTheme.typography.labelLarge.fontSize,
                        ),
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

@SmallScreenPreview
@Composable
private fun StreakWidgetPreviewSmall1() {
    LCPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StreakWidget(StreakUI.Dead(150))
            StreakWidget(StreakUI.Freeze.OneFreeze)
            StreakWidget(StreakUI.Freeze.NoneFreeze)
        }
    }
}

@SmallScreenPreview
@Composable
private fun StreakWidgetPreviewSmall2() {
    LCPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StreakWidget(StreakUI.NeverStarted)
            StreakWidget(StreakUI.TodayCompleted(1, subtitle = R.string.home_streak_completed_any3))
            StreakWidget(StreakUI.TodayNotCompleted(2))
        }
    }
}
