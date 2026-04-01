package com.linguaceleris.home.impl.domain.mapper

import com.linguaceleris.home.impl.ui.model.DayQuizzesUI
import com.linguaceleris.quiz.model.DayDTO

fun DayDTO.toUi(): DayQuizzesUI = DayQuizzesUI(
    advanced = advanced,
    basic = basic,
    intermediate = intermediate,
)
