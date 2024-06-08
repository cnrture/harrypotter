package org.canerture.harrypotter.data

import com.varabyte.kobweb.browser.http.http
import kotlinx.browser.window
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.canerture.harrypotter.data.model.Character
import org.canerture.harrypotter.data.model.GetCharacterResponse

suspend fun getCharacters(): Result<List<Character>> {
    val url = "https://api.canerture.com/harrypotterapp/characters"
    return try {
        val response = window.http.get(url).parseData<GetCharacterResponse>()
        if (response.status == 200) {
            Result.success(response.data.orEmpty())
        } else {
            Result.failure(Exception(response.message.orEmpty()))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

inline fun <reified T : Any> ByteArray?.parseData(): T {
    val json = this?.decodeToString().orEmpty()
    return Json.decodeFromString(json)
}
