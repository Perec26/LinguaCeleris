package com.linguaceleris.quiz.impl.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.linguaceleris.designsystem.theme.LinguaCelerisTheme
import com.linguaceleris.designsystem.widgets.DefaultImageButton
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.fillInBlankMock
import com.linguaceleris.quiz.impl.ui.imageSelectWordTranslation
import com.linguaceleris.quiz.impl.ui.listenSelectTranslationMock
import com.linguaceleris.quiz.impl.ui.model.TaskContentType
import com.linguaceleris.quiz.impl.ui.model.TaskUI
import com.linguaceleris.quiz.impl.ui.model.WordCardUI
import com.linguaceleris.quiz.impl.ui.selectCorrectAnswerMock

@Composable
internal fun TaskContentWidget(
    modifier: Modifier = Modifier,
    task: TaskUI.SelectCorrectAnswer,
    onAudioClick: (String?) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 48.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        when (task.type.contentType) {
            TaskContentType.TEXT -> TextTaskContent(task.question)
            TaskContentType.IMAGE -> ImageTaskContent(task.question)
            TaskContentType.AUDIO -> AudioTaskContent(task.question, onAudioClick)
            TaskContentType.TEXT_AUDIO -> TextAudioTaskContent(task.question, onAudioClick)
        }
    }
}

@Composable
private fun TextTaskContent(task: WordCardUI) {
    Text(
        text = task.text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun AudioTaskContent(task: WordCardUI, onAudioClick: (String?) -> Unit) {
    DefaultImageButton(
        modifier = Modifier,
        iconModifier = Modifier
            .size(64.dp)
            .aspectRatio(1f),
        drawable = R.drawable.quiz_volume_up,
        onClick = { onAudioClick(task.audio) },
    )
}

@Composable
private fun TextAudioTaskContent(task: WordCardUI, onAudioClick: (String?) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DefaultImageButton(
            iconModifier = Modifier
                .size(24.dp)
                .aspectRatio(1f),
            drawable = R.drawable.quiz_volume_up,
            onClick = { onAudioClick(task.audio) },
        )
        Text(
            text = task.text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
private fun ImageTaskContent(task: WordCardUI) {
    val model = if (LocalInspectionMode.current) {
        com.linguaceleris.designsystem.R.drawable.landscape_placeholder
    } else {
        task.image
    }
    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(48.dp)),
        model = model,
        contentDescription = null,
    )
}

@Composable
@Preview
private fun TaskContentWidgetTextPreview() {
    LinguaCelerisTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TaskContentWidget(
                    task = fillInBlankMock,
                ) {}
            }
        }
    }
}

@Composable
@Preview
private fun TaskContentWidgetAudioPreview() {
    LinguaCelerisTheme {
        Surface {
            TaskContentWidget(
                task = listenSelectTranslationMock,
            ) {}
        }
    }
}

@Composable
@Preview
private fun TaskContentWidgetTextAudioPreview() {
    LinguaCelerisTheme {
        Surface {
            TaskContentWidget(
                task = selectCorrectAnswerMock,
            ) {}
        }
    }
}

@Composable
@Preview
private fun TaskContentWidgetImageAudioPreview() {
    LinguaCelerisTheme {
        Surface {
            TaskContentWidget(
                task = imageSelectWordTranslation,
            ) {}
        }
    }
}
