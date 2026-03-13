package com.linguaceleris.quiz

import com.linguaceleris.network.ImageLoadService
import com.linguaceleris.quiz.dataSource.QuizDataSource
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val imageLoadService: ImageLoadService,
    private val dataSource: QuizDataSource,
) {

    suspend fun getQuiz(): QuizDTO? {
        val quiz = dataSource.getQuiz() ?: return null
        val images = quiz.tasks
            .map(TaskDTO::data)
            .flatMap(TaskDataDTO::getImages)
        preloadImages(images)
        return quiz
    }

    private suspend fun preloadImages(images: List<String>) {
        images.forEach { imageLoadService.loadImage(it) }
    }
}
