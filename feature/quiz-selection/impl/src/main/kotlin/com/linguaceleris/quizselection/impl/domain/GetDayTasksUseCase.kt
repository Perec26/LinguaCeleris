package com.linguaceleris.quizselection.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.quizselection.impl.domain.mapper.toUi
import com.linguaceleris.quizselection.impl.ui.model.DayQuizzesUI
import com.linguaceleris.services.TrustedTimeManager
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class GetDayTasksUseCase @Inject constructor(
    private val repository: QuizRepository,
    private val timeManager: TrustedTimeManager,
) {

    suspend operator fun invoke(): DayQuizzesUI {
        val schedule = repository.getSchedule() ?: return DayQuizzesUI()
        val currentDay = timeManager.getLocalDate()
        val day = schedule.days.firstOrNull {
            LocalDate.parse(it.date) == currentDay
        } ?: return DayQuizzesUI()
        return day.toUi()
    }
}
