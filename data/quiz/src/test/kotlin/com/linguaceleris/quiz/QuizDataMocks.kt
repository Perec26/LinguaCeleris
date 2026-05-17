package com.linguaceleris.quiz

import com.linguaceleris.network.AudioLoadService
import com.linguaceleris.network.ImageLoadService
import com.linguaceleris.quiz.dataSource.QuizDataSource
import com.linguaceleris.quiz.model.DayDTO
import com.linguaceleris.quiz.model.MatchingPairDTO
import com.linguaceleris.quiz.model.QuizDTO
import com.linguaceleris.quiz.model.QuizLevelDTO
import com.linguaceleris.quiz.model.ScheduleDTO
import com.linguaceleris.quiz.model.TaskDTO
import com.linguaceleris.quiz.model.TaskDataDTO
import com.linguaceleris.quiz.model.VariantDTO
import com.linguaceleris.services.time.TrustedTimeManager
import io.mockk.mockk
import kotlinx.datetime.LocalDate

internal object QuizDataMocks {
    val imageLoadService = mockk<ImageLoadService>(relaxed = true)
    val audioLoadService = mockk<AudioLoadService>(relaxed = true)
    val timeManager = mockk<TrustedTimeManager>()
    val dataSource = mockk<QuizDataSource>()
    val storage = mockk<QuizInMemoryStorage>(relaxed = true)

    val quizIdMock = "test_quiz_id"
    val dateMock = LocalDate(2024, 5, 16)
    val levelMock = QuizLevelDTO.BASIC
    val scheduleMock = mockk<ScheduleDTO>()

    val dayMock = DayDTO(
        date = dateMock,
        basic = "basic_id",
        intermediate = "inter_id",
        advanced = "adv_id",
    )

    val fullScheduleMock = ScheduleDTO(
        contractVersion = 1,
        days = listOf(dayMock),
    )

    val variantMock = VariantDTO(
        audio = "audio_url",
        text = "text",
        image = "image_url",
    )

    val taskDataMock = TaskDataDTO.ChooseCorrectDTO(
        question = variantMock,
        answer = variantMock,
        options = listOf(variantMock),
    )

    val tasksMock = listOf(TaskDTO.SelectTranslationDTO(id = "1", data = taskDataMock))

    val quizMock = QuizDTO(
        contractVersion = "1",
        dailyQuizId = "daily_id",
        extraPool = emptyList(),
        tasks = tasksMock,
    )

    val matchingPairMock = MatchingPairDTO(
        left = VariantDTO(audio = "audio_l", text = "left", image = "image_l"),
        right = VariantDTO(audio = "audio_r", text = "right", image = "image_r"),
    )

    val matchingDataMock = TaskDataDTO.MatchingDataDTO(pairs = listOf(matchingPairMock))

    val quizWithMatchingMock = quizMock.copy(
        tasks = tasksMock + TaskDTO.MatchingDTO(id = "2", data = matchingDataMock),
    )

    val antonymChoiceJson = """
        {
            "id": "1",
            "type": "antonym_choice",
            "data": {
                "question": { "text": "hot", "audio": null, "image": null },
                "answer": { "text": "cold", "audio": null, "image": null },
                "options": [
                    { "text": "cold", "audio": null, "image": null },
                    { "text": "warm", "audio": null, "image": null }
                ]
            }
        }
    """.trimIndent()

    val matchingJson = """
        {
            "id": "2",
            "type": "matching",
            "data": {
                "pairs": [
                    {
                        "left": { "text": "apple", "audio": null, "image": null },
                        "right": { "text": "яблоко", "audio": null, "image": null }
                    }
                ]
            }
        }
    """.trimIndent()

    val unknownTypeJson = """
        {
            "id": "3",
            "type": "some_new_type",
            "data": {}
        }
    """.trimIndent()

    val listenSelectTranslationJson = """
        {
            "id": "4",
            "type": "listen_select_translation",
            "data": {
                "question": { "text": "apple", "audio": "apple.mp3", "image": null },
                "answer": { "text": "яблоко", "audio": null, "image": null },
                "options": [
                    { "text": "яблоко", "audio": null, "image": null },
                    { "text": "банан", "audio": null, "image": null }
                ]
            }
        }
    """.trimIndent()

    val audioMatchingJson = """
        {
            "id": "5",
            "type": "audio_matching",
            "data": { "pairs": [] }
        }
    """.trimIndent()

    val fillInBlankJson = """
        {
            "id": "6",
            "type": "fill_in_blank",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()

    val findCorrectJson = """
        {
            "id": "7",
            "type": "find_correct",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()

    val homophonesJson = """
        {
            "id": "8",
            "type": "homophones",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()

    val imageSelectWordJson = """
        {
            "id": "9",
            "type": "image_select_word",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()

    val selectAudioJson = """
        {
            "id": "10",
            "type": "select_audio",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()

    val selectTranslationJson = """
        {
            "id": "11",
            "type": "select_translation",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()

    val synonymChoiceJson = """
        {
            "id": "12",
            "type": "synonym_choice",
            "data": {
                "question": { "text": "q", "audio": null, "image": null },
                "answer": { "text": "a", "audio": null, "image": null },
                "options": []
            }
        }
    """.trimIndent()
}
