package org.canerture.harrypotter.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCharacterResponse(
    val data: List<Character>?,
    val status: Int?,
    val message: String?,
)
