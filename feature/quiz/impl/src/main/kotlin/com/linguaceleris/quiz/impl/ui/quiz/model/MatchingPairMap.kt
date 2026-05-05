package com.linguaceleris.quiz.impl.ui.quiz.model

internal class MatchingPairMap(pairs: List<MatchingPairUI>) {
    private val map = buildMap {
        pairs.forEach { pair ->
            put(pair.first, pair)
            put(pair.second, pair)
        }
    }

    operator fun get(key: WordCardUI?): MatchingPairUI? = key?.let { map[key] }

    fun getTranslation(key: WordCardUI): WordCardUI? {
        val pair = map[key] ?: return null
        return if (pair.first == key) pair.second else pair.first
    }
}
