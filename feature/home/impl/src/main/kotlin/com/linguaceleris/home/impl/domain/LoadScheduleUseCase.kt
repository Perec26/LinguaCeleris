package com.linguaceleris.home.impl.domain

import com.linguaceleris.quiz.QuizRepository
import javax.inject.Inject

internal class LoadScheduleUseCase @Inject constructor(private val repository: QuizRepository) {

    suspend operator fun invoke() = repository.loadSchedule()
}
