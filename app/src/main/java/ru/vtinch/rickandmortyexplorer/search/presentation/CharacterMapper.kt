package ru.vtinch.rickandmortyexplorer.search.presentation

import ru.vtinch.rickandmortyexplorer.search.domain.model.Character

class CharacterMapper: Character.Mapper<CharacterUi> {
    override fun map(
        id: Int,
        name: String,
        imageUrl: String,
        status: String,
        species: String
    ) = CharacterUi(
        id = id,
        name = name,
        status = status,
        species = species,
        imageUrl = imageUrl
    )
}