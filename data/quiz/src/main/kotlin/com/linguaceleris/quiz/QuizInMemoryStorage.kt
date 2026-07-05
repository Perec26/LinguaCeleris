package com.linguaceleris.quiz

import com.linguaceleris.quiz.model.DayDTO
import com.linguaceleris.quiz.model.QuizLevelDTO
import com.linguaceleris.quiz.model.ScheduleDTO
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.datetime.LocalDate

@Singleton
class QuizInMemoryStorage @Inject constructor() {

    private var schedule: ScheduleDTO? = null

    fun saveSchedule(schedule: ScheduleDTO) {
        this.schedule = schedule
    }

    fun getQuizId(date: LocalDate, level: QuizLevelDTO): String? {
        val days = schedule?.days ?: return null
        days.firstOrNull { it.date == date }?.getLevel(level)?.apply { return this }
        days.lastOrNull()?.getLevel(level)?.apply { return this }
        return null
    }

    private fun DayDTO.getLevel(level: QuizLevelDTO): String? = when (level) {
        QuizLevelDTO.BASIC -> basic
        QuizLevelDTO.INTERMEDIATE -> intermediate
        QuizLevelDTO.ADVANCED -> advanced
    }
}
