package ru.vtinch.rickandmortyexplorer.search.domain.repository

import ru.vtinch.rickandmortyexplorer.search.domain.model.Character

interface SearchCharacter {
    suspend fun search(query: String = ""): List<Character>

    suspend fun getCharacterById(id: Int)
}