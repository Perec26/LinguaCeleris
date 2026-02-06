package com.linguaceleris.network

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.serializer

inline fun <reified T> DocumentSnapshot.toDataClass(
    json: Json = Json { ignoreUnknownKeys = true },
): T? {
    val data = this.data ?: return null
    val dataWithId = data.toMutableMap()
    dataWithId["id"] = this.id

    val jsonElement = dataWithId.toJsonElement()
    return json.decodeFromJsonElement<T>(serializer<T>(), jsonElement)
}

fun Any?.toJsonElement(): JsonElement = when (this) {
    null -> JsonNull

    is Number -> JsonPrimitive(this)

    is Boolean -> JsonPrimitive(this)

    is String -> JsonPrimitive(this)

    is List<*> -> JsonArray(this.map { it.toJsonElement() })

    is Map<*, *> -> {
        val jsonMap = this.mapKeys { it.key.toString() }
            .mapValues { it.value.toJsonElement() }
        JsonObject(jsonMap)
    }

    else -> JsonPrimitive(this.toString())
}
