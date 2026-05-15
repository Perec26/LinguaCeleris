package com.linguaceleris.home.impl.domain

import com.linguaceleris.quiz.QuizRepository
import com.linguaceleris.testing.ExcludeFromKover
import javax.inject.Inject

@ExcludeFromKover
internal class LoadScheduleUseCase @Inject constructor(private val repository: QuizRepository) {

    suspend operator fun invoke() = repository.loadSchedule()
}
