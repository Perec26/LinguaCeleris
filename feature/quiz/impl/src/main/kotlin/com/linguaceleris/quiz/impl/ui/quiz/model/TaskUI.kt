package com.linguaceleris.quiz.impl.ui.quiz.model

internal sealed class TaskUI(open val type: TaskTypeUI) {

    data class SelectCorrectAnswer(
        override val type: TaskTypeUI,
        val question: WordCardUI,
        val correctAnswer: WordCardUI,
        val answerVariants: List<WordCardUI>,
        val selectedVariant: WordCardUI? = null,
        val isChecked: Boolean = false,
    ) : TaskUI(type) {
        val isCorrect = correctAnswer == selectedVariant
    }

    data class Matching(
        override val type: TaskTypeUI,
        val pairs: List<MatchingPairUI>,
        val originalVariants: List<WordCardUI> = emptyList(),
        val translationVariants: List<WordCardUI> = emptyList(),
        val disabledVariants: List<WordCardUI> = emptyList(),
        val selectedVariant: WordCardUI? = null,
        val errorVariant: WordCardUI? = null,
    ) : TaskUI(type) {
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

internal data class MatchingPairUI(val original: WordCardUI, val translation: WordCardUI)

internal data class WordCardUI(
    val text: String,
    val audio: String?,
    val image: String? = null,
)
