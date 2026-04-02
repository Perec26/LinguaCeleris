package com.linguaceleris.home.impl.domain

import com.linguaceleris.home.impl.domain.mapper.toUi
import com.linguaceleris.home.impl.ui.model.DayQuizzesUI
import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.services.time.TrustedTimeManager
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
