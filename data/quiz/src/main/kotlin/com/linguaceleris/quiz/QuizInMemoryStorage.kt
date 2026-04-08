package com.linguaceleris.quiz

import com.linguaceleris.quiz.model.QuizDifficultyDTO
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

    fun getQuizId(date: LocalDate, difficulty: QuizDifficultyDTO): String? {
        val dayQuizzes = schedule?.days?.firstOrNull { it.date == date } ?: return null
        return when (difficulty) {
            QuizDifficultyDTO.BASIC -> dayQuizzes.basic
            QuizDifficultyDTO.INTERMEDIATE -> dayQuizzes.intermediate
            QuizDifficultyDTO.ADVANCED -> dayQuizzes.advanced
        }
    }
}
