package com.linguaceleris.quiz

import com.linguaceleris.lib.ProgressWrapper
import com.linguaceleris.network.AudioLoadService
import com.linguaceleris.network.ImageLoadService
import com.linguaceleris.quiz.dataSource.QuizDataSource
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.QuizLevelDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.services.time.TrustedTimeManager
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    suspend fun getTasks(level: QuizLevelDTO): Flow<ProgressWrapper<QuizDTO>> {
        val date = timeManager.getCurrentDate()
        val quizId = storage.getQuizId(date, level) ?: return flow {
            emit(ProgressWrapper.Failure(IllegalStateException("QuizId is null")))
        }
        return getQuiz(quizId)
    }

    private fun getQuiz(quizId: String) = flow {
        val quiz = dataSource.getQuiz(quizId)

        if (quiz == null) {
            emit(ProgressWrapper.Failure(IllegalStateException("Quiz is null")))
            return@flow
        }

        val images = quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getImages)

        val audios = quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getAudios)
        val sum = images.size + audios.size
        var current = 0

        emit(ProgressWrapper.Loading(0f))

        images.forEach {
            imageLoadService.loadImage(it)
            current++
            emit(ProgressWrapper.Loading(current.toFloat() / sum))
        }

        audios.forEach {
            audioLoadService.loadAudio(it)
            current++
            emit(ProgressWrapper.Loading(current.toFloat() / sum))
        }

        emit(ProgressWrapper.Success(quiz))
    }
}
