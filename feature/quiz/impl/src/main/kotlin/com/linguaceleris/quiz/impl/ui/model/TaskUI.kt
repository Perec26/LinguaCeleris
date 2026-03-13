package com.linguaceleris.quiz.impl.ui.model

import androidx.annotation.StringRes

sealed class TaskUI {
    @get:StringRes
    abstract val text: Int

    @get:StringRes
    abstract val info: Int

    data class SelectCorrectAnswer(
        override val text: Int,
        override val info: Int,
        val question: WordCardUI,
        val correctAnswer: WordCardUI,
        val answerVariants: List<WordCardUI>,
        val selectedVariant: WordCardUI? = null,
        val isChecked: Boolean = false,
    ) : TaskUI()

    data class Matching(
        override val text: Int,
        override val info: Int,
        val pairs: List<MatchingPairUI>,
        val originalVariants: List<WordCardUI> = emptyList(),
        val translationVariants: List<WordCardUI> = emptyList(),
        val disabledVariants: List<WordCardUI> = emptyList(),
        val selectedVariant: WordCardUI? = null,
        val errorVariant: WordCardUI? = null,
    ) : TaskUI() {
        val hasError = errorVariant != null
        val isDone = disabledVariants.size >= pairs.size * 2
        val selectedVariantFromOriginal = originalVariants.contains(selectedVariant)
        val selectedPair =
            pairs.find { it.original == selectedVariant || it.translation == selectedVariant }

        val correctVariant = getCorrect()

        fun isReselect(variant: WordCardUI): Boolean = if (selectedVariantFromOriginal) {
            originalVariants.contains(variant)
        } else {
            translationVariants.contains(variant)
        }

        private fun getCorrect(): WordCardUI? {
            if (selectedPair?.original == selectedVariant) return selectedPair?.translation
            if (selectedPair?.translation == selectedVariant) return selectedPair?.original
            return null
        }
    }
}

data class MatchingPairUI(
    val original: WordCardUI,
    val translation: WordCardUI,
)

data class WordCardUI(
    val text: String,
    val audio: String?,
    val image: String? = null,
)
