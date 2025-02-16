package ru.vtinch.rickandmortyexplorer.search.domain.model

data class CharacterDetail(
    private val id: Int,
    private val name: String,
    private val imageUrl: String,
    private val status: String,
    private val location: String,
    private val species: String,
    private val gender: String,
    private val episodes: List<Pair<String, String>>
) {

    fun <T : Any> map(mapper: Mapper<T>): T = mapper.map(
        id = id,
        name = name,
        imageUrl = imageUrl,
        status = status,
        location = location,
        species = species,
        gender = gender,
        episodes = episodes
    )

    interface Mapper<T : Any> {
        fun map(
            id: Int,
            name: String,
            imageUrl: String,
            status: String,
            location: String,
            species: String,
            gender: String,
            episodes: List<Pair<String, String>>
        ): T
    }
}

