package com.linguaceleris.quiz.impl.ui.quiz.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.linguaceleris.designsystem.widgets.LCPreview
import com.linguaceleris.designsystem.widgets.buttons.ButtonSize
import com.linguaceleris.designsystem.widgets.buttons.LCFilledImageButton
import com.linguaceleris.quiz.impl.R
import com.linguaceleris.quiz.impl.ui.quiz.fillInBlankMock
import com.linguaceleris.quiz.impl.ui.quiz.imageSelectWordTranslation
import com.linguaceleris.quiz.impl.ui.quiz.listenSelectTranslationMock
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskContentType
import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import com.linguaceleris.quiz.impl.ui.quiz.selectCorrectAnswerMock

@Composable
internal fun TaskContentWidget(
    modifier: Modifier = Modifier,
    task: TaskUI.SelectCorrectAnswer,
    onAudioClick: (String?) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
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
        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
        text = task.text,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displaySmall,
    )
}

@Composable
private fun AudioTaskContent(task: WordCardUI, onAudioClick: (String?) -> Unit) {
    LCFilledImageButton(
        modifier = Modifier.padding(vertical = 16.dp),
        buttonSize = ButtonSize.LARGE,
        drawable = R.drawable.quiz_volume_up,
        onClick = { onAudioClick(task.audio) },
    )
}

@Composable
private fun TextAudioTaskContent(task: WordCardUI, onAudioClick: (String?) -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LCFilledImageButton(
            modifier = Modifier,
            iconWith = IconButtonDefaults.IconButtonWidthOption.Wide,
            drawable = R.drawable.quiz_volume_up,
            onClick = { onAudioClick(task.audio) },
        )
        Text(
            text = task.text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall,
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
            .padding(vertical = 16.dp)
            .size(200.dp)
            .clip(RoundedCornerShape(48.dp)),
        model = model,
        contentDescription = null,
    )
}

@Composable
@Preview
private fun TaskContentWidgetTextPreview() {
    LCPreview {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TaskContentWidget(task = fillInBlankMock) {}
        }
    }
}

@Composable
@Preview
private fun TaskContentWidgetAudioPreview() {
    LCPreview {
        TaskContentWidget(task = listenSelectTranslationMock) {}
    }
}

@Composable
@Preview
private fun TaskContentWidgetTextAudioPreview() {
    LCPreview {
        TaskContentWidget(task = selectCorrectAnswerMock) {}
    }
}

@Composable
@Preview
private fun TaskContentWidgetImageAudioPreview() {
    LCPreview {
        TaskContentWidget(task = imageSelectWordTranslation) {}
    }
}
