package org.canerture.harrypotter.pages

import org.canerture.harrypotter.data.model.Character

sealed interface CharacterListState {
    data object Loading : CharacterListState
    data class Success(val characters: List<Character>) : CharacterListState
    data class Error(val message: String) : CharacterListState
}