package com.linguaceleris.quiz

import com.linguaceleris.network.AudioLoadService
import com.linguaceleris.network.ImageLoadService
import com.linguaceleris.quiz.dataSource.QuizDataSource
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.QuizLevelDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.services.time.TrustedTimeManager
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val imageLoadService: ImageLoadService,
    private val audioLoadService: AudioLoadService,
    private val timeManager: TrustedTimeManager,
    private val dataSource: QuizDataSource,
    private val storage: QuizInMemoryStorage,
) {

    suspend fun loadSchedule() {
        val schedule = dataSource.getSchedule() ?: error("Can't load schedule")
        storage.saveSchedule(schedule)
    }

    suspend fun getTasks(level: QuizLevelDTO): QuizDTO? {
        val date = timeManager.getCurrentDate()
        val quizId = storage.getQuizId(date, level) ?: return null
        return getQuiz(quizId)
    }

    private suspend fun getQuiz(quizId: String): QuizDTO? {
        val quiz = dataSource.getQuiz(quizId) ?: return null

        quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getImages)
            .forEach { imageLoadService.loadImage(it) }

        quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getAudios)
            .forEach { audioLoadService.loadAudio(it) }

        return quiz
    }
}
