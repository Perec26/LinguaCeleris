package com.linguaceleris.quiz.impl.domain

import com.linguaceleris.quiz.impl.ui.quiz.model.TaskUI
import com.linguaceleris.quiz.impl.ui.quiz.model.WordCardUI
import javax.inject.Inject

internal class SelectVariantUseCase @Inject constructor() {

    operator fun invoke(task: TaskUI, variant: WordCardUI): TaskUI = when (task) {
        is TaskUI.SelectCorrectAnswer -> task.copy(selectedVariant = variant)
        is TaskUI.Matching -> task.selectVariant(variant)
    }
}

private fun TaskUI.Matching.selectVariant(variant: WordCardUI): TaskUI.Matching {
    if (selectedVariant == null) return copy(selectedVariant = variant)
    if (isReselect(variant)) return copy(selectedVariant = variant)
    if (selectedPair?.first == variant || selectedPair?.second == variant) {
        return copy(
            disabledVariants = disabledVariants + variant + selectedVariant,
            selectedVariant = null,
        )
    }
    return copy(errorVariant = variant)
}
