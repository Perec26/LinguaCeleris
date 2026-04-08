package com.linguaceleris.quiz

import com.linguaceleris.network.AudioLoadService
import com.linguaceleris.network.ImageLoadService
import com.linguaceleris.quiz.dataSource.QuizDataSource
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.QuizDifficultyDTO
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

    suspend fun getQuiz(quizId: String): QuizDTO? {
        val quiz = dataSource.getQuiz(quizId) ?: return null

        val images = quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getImages)
        preloadImages(images)

        val audios = quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getAudios)

        preloadAudios(audios)
        return quiz
    }

    private suspend fun preloadAudios(audios: List<String>) {
        audios.forEach { audioLoadService.loadAudio(it) }
    }

    private suspend fun preloadImages(images: List<String>) {
        images.forEach { imageLoadService.loadImage(it) }
    }

    suspend fun loadSchedule() {
        val schedule = dataSource.getSchedule() ?: error("Can't load schedule")
        storage.saveSchedule(schedule)
    }

    suspend fun getTasks(difficulty: QuizDifficultyDTO): QuizDTO? {
        val date = timeManager.getCurrentDate()
        val quizId = storage.getQuizId(date, difficulty) ?: return null
        return getQuiz(quizId)
    }
}
