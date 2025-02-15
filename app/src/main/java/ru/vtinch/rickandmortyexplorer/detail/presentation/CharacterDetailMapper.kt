package ru.vtinch.rickandmortyexplorer.detail.presentation

import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail

class CharacterDetailMapper : CharacterDetail.Mapper<CharacterDetailUi> {
    override fun map(
        id: Int,
        name: String,
        imageUrl: String,
        status: String,
        location: String,
        species: String,
        gender: String,
        episodes: List<Pair<String, String>>
    ): CharacterDetailUi {
        val list = episodes.map {
            val (title, episode) = it
            Episode(title, episode)
        }
        return CharacterDetailUi(
            id = id,
            name = name,
            status = status,
            imageUrl = imageUrl,
            species = species,
            gender = gender,
            location = location,
            episodes = list
        )
    }
}