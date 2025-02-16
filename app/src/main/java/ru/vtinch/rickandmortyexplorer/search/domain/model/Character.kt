package ru.vtinch.rickandmortyexplorer.search.domain.model

data class Character(
    private val id: Int,
    private val name: String,
    private val imageUrl: String,
    private val status: String,
    private val species: String,
) {
    fun <T : Any> map(mapper: Mapper<T>): T =
        mapper.map(id, name, imageUrl, status, species)

    interface Mapper<T : Any> {
        fun map(id: Int, name: String, imageUrl: String, status: String, species: String): T
    }
}
