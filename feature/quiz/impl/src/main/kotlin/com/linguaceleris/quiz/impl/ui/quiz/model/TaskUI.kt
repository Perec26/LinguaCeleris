package com.linguaceleris.quiz.impl.ui.quiz.model

internal typealias MatchingPairUI = Pair<WordCardUI, WordCardUI>

internal sealed class TaskUI(open val id: String, open val type: TaskTypeUI) {

    data class SelectCorrectAnswer(
        override val id: String,
        override val type: TaskTypeUI,
        val question: WordCardUI,
        val correctAnswer: WordCardUI,
        val answerVariants: List<WordCardUI>,
        val selectedVariant: WordCardUI? = null,
        val isChecked: Boolean = false,
    ) : TaskUI(id, type) {
        val isCorrect = correctAnswer == selectedVariant
    }

    data class Matching(
        override val id: String,
        override val type: TaskTypeUI,
        val pairs: List<MatchingPairUI>,
        val originalVariants: List<WordCardUI> = emptyList(),
        val translationVariants: List<WordCardUI> = emptyList(),
        val disabledVariants: List<WordCardUI> = emptyList(),
        val selectedVariant: WordCardUI? = null,
        val errorVariant: WordCardUI? = null,
    ) : TaskUI(id, type) {
        val pairsMap = MatchingPairMap(pairs)
        val hasError = errorVariant != null
        val isDone = disabledVariants.size >= pairs.size * 2
        val selectedVariantFromOriginal = originalVariants.contains(selectedVariant)
        val selectedPair = pairsMap[selectedVariant]

        val correctVariant = getCorrect()

        fun isReselect(variant: WordCardUI): Boolean = if (selectedVariantFromOriginal) {
            originalVariants.contains(variant)
        } else {
            translationVariants.contains(variant)
        }

        private fun getCorrect() = selectedVariant?.let {
            pairsMap.getTranslation(selectedVariant)
        }
    }
}

internal data class WordCardUI(
    val text: String,
    val audio: String?,
    val image: String? = null,
)
