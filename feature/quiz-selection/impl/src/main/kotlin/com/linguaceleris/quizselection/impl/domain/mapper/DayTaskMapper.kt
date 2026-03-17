package com.linguaceleris.quizselection.impl.domain.mapper

import com.linguaceleris.quiz.model.DayDTO
import com.linguaceleris.quizselection.impl.ui.model.DayQuizzesUI

fun DayDTO.toUi(): DayQuizzesUI = DayQuizzesUI(
    advanced = advanced,
    basic = basic,
    intermediate = intermediate,
)
