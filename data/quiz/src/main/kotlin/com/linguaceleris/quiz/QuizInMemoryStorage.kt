package com.linguaceleris.quiz

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
        val dayQuizzes = schedule?.days?.firstOrNull { it.date == date } ?: return null
        return when (level) {
            QuizLevelDTO.BASIC -> dayQuizzes.basic
            QuizLevelDTO.INTERMEDIATE -> dayQuizzes.intermediate
            QuizLevelDTO.ADVANCED -> dayQuizzes.advanced
        }
    }
}
