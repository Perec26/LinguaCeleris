package com.linguaceleris.quiz.model

import com.linguaceleris.quiz.QuizDataMocks.antonymChoiceJson
import com.linguaceleris.quiz.QuizDataMocks.audioMatchingJson
import com.linguaceleris.quiz.QuizDataMocks.fillInBlankJson
import com.linguaceleris.quiz.QuizDataMocks.findCorrectJson
import com.linguaceleris.quiz.QuizDataMocks.homophonesJson
import com.linguaceleris.quiz.QuizDataMocks.imageSelectWordJson
import com.linguaceleris.quiz.QuizDataMocks.listenSelectTranslationJson
import com.linguaceleris.quiz.QuizDataMocks.matchingJson
import com.linguaceleris.quiz.QuizDataMocks.selectAudioJson
import com.linguaceleris.quiz.QuizDataMocks.selectTranslationJson
import com.linguaceleris.quiz.QuizDataMocks.synonymChoiceJson
import com.linguaceleris.quiz.QuizDataMocks.unknownTypeJson
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.json.Json

class TaskSerializerTest : BehaviorSpec(
    {
        val json = Json { ignoreUnknownKeys = true }

        Given("A JSON string with Task data") {
            When("type is supported") {
                Then("it should deserialize into correct DTO") {
                    json.decodeFromString<TaskDTO>(antonymChoiceJson).shouldBeInstanceOf<TaskDTO.AntonymChoiceDTO>()
                    json.decodeFromString<TaskDTO>(audioMatchingJson).shouldBeInstanceOf<TaskDTO.AudioMatchingDTO>()
                    json.decodeFromString<TaskDTO>(fillInBlankJson).shouldBeInstanceOf<TaskDTO.FillInTheBlankDTO>()
                    json.decodeFromString<TaskDTO>(findCorrectJson).shouldBeInstanceOf<TaskDTO.FindCorrectDTO>()
                    json.decodeFromString<TaskDTO>(homophonesJson).shouldBeInstanceOf<TaskDTO.HomophonesDTO>()
                    json.decodeFromString<TaskDTO>(imageSelectWordJson)
                        .shouldBeInstanceOf<TaskDTO.ImageSelectWordTranslationDTO>()
                    json.decodeFromString<TaskDTO>(listenSelectTranslationJson)
                        .shouldBeInstanceOf<TaskDTO.ListenSelectTranslationDTO>()
                    json.decodeFromString<TaskDTO>(matchingJson).shouldBeInstanceOf<TaskDTO.MatchingDTO>()
                    json.decodeFromString<TaskDTO>(selectAudioJson).shouldBeInstanceOf<TaskDTO.SelectAudioDTO>()
                    json.decodeFromString<TaskDTO>(selectTranslationJson)
                        .shouldBeInstanceOf<TaskDTO.SelectTranslationDTO>()
                    json.decodeFromString<TaskDTO>(synonymChoiceJson).shouldBeInstanceOf<TaskDTO.SynonymChoiceDTO>()
                }
            }

            When("type is unknown") {
                Then("it should deserialize into UnknowQuestionDTO") {
                    json.decodeFromString<TaskDTO>(unknownTypeJson).shouldBeInstanceOf<TaskDTO.UnknowQuestionDTO>()
                }
            }
        }
    },
)
