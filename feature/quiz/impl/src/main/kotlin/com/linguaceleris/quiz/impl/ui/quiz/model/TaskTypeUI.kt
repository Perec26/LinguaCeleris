package com.linguaceleris.quiz.impl.ui.quiz.model

import androidx.annotation.StringRes
import com.linguaceleris.quiz.impl.R

internal sealed class TaskTypeUI(
    @param:StringRes val text: Int,
    @param:StringRes val info: Int,
    val contentType: TaskContentType,
    val answerType: AnswerType
) {

    object AntonymChoice :
        TaskTypeUI(
            text = R.string.quiz_short_antonym_choice,
            info = R.string.quiz_long_antonym_choice,
            contentType = TaskContentType.TEXT_AUDIO,
            answerType = AnswerType.TEXT
        )

    object AudioMatching :
        TaskTypeUI(
            text = R.string.quiz_short_audio_matching,
            info = R.string.quiz_long_audio_matching,
            contentType = TaskContentType.AUDIO,
            answerType = AnswerType.AUDIO
        )

    object FillInBlank :
        TaskTypeUI(
            text = R.string.quiz_short_fill_in_blank,
            info = R.string.quiz_long_fill_in_blank,
            contentType = TaskContentType.TEXT,
            answerType = AnswerType.TEXT
        )

    object FindCorrect :
        TaskTypeUI(
            text = R.string.quiz_short_find_correct,
            info = R.string.quiz_long_find_correct,
            contentType = TaskContentType.TEXT_AUDIO,
            answerType = AnswerType.TEXT
        )

    object Homophones :
        TaskTypeUI(
            text = R.string.quiz_short_homophones,
            info = R.string.quiz_long_homophones,
            contentType = TaskContentType.TEXT,
            answerType = AnswerType.TEXT
        )

    object ImageSelectWordTranslation :
        TaskTypeUI(
            text = R.string.quiz_short_image_select_word,
            info = R.string.quiz_long_image_select_word,
            contentType = TaskContentType.IMAGE,
            answerType = AnswerType.TEXT
        )

    object ListenSelectTranslation :
        TaskTypeUI(
            text = R.string.quiz_short_listen_select_translation,
            info = R.string.quiz_long_listen_select_translation,
            contentType = TaskContentType.AUDIO,
            answerType = AnswerType.TEXT
        )

    object Matching :
        TaskTypeUI(
            text = R.string.quiz_short_matching,
            info = R.string.quiz_long_matching,
            contentType = TaskContentType.TEXT,
            answerType = AnswerType.TEXT
        )

    object SelectAudio :
        TaskTypeUI(
            text = R.string.quiz_short_select_audio,
            info = R.string.quiz_long_select_audio,
            contentType = TaskContentType.TEXT,
            answerType = AnswerType.AUDIO
        )

    object SelectTranslation :
        TaskTypeUI(
            text = R.string.quiz_short_select_translation,
            info = R.string.quiz_long_select_translation,
            contentType = TaskContentType.TEXT_AUDIO,
            answerType = AnswerType.TEXT
        )

    object SynonymChoice :
        TaskTypeUI(
            text = R.string.quiz_short_synonym_choice,
            info = R.string.quiz_long_synonym_choice,
            contentType = TaskContentType.TEXT_AUDIO,
            answerType = AnswerType.TEXT
        )

    object Unknown : TaskTypeUI(
        text = R.string.quiz_short_unknown,
        info = R.string.quiz_long_unknown,
        contentType = TaskContentType.TEXT,
        answerType = AnswerType.TEXT
    )
}

internal enum class TaskContentType {
    TEXT,
    IMAGE,
    AUDIO,
    TEXT_AUDIO
}

internal enum class AnswerType {
    TEXT,
    AUDIO,
}
