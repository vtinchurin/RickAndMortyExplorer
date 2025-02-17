package ru.vtinch.rickandmortyexplorer.search.data.cloud

import android.util.Log

interface CloudDataSource {

    suspend fun fetch(query: String): List<CharacterDto>

    suspend fun getCharacterById(id: Int): CharacterDto

    suspend fun getEpisodes(episodes: List<Int>): ArrayList<Episode>

    class Base(private val service: CharacterService) : CloudDataSource {

        override suspend fun fetch(query: String): List<CharacterDto> {
            val result = if (query.isNotEmpty()) {
                service.search(query).execute()
            } else service.getAll().execute()

            if (result.isSuccessful) {
                val body = result.body()!!
                return body.characters
            } else {
                throw IllegalStateException(result.errorBody()?.string())
            }
        }

        override suspend fun getCharacterById(id: Int): CharacterDto {
            val result = service.getCharacterById(id).execute()
            if (result.isSuccessful) {
                return result.body()!!
            } else throw IllegalStateException(result.errorBody()?.string())
        }

        override suspend fun getEpisodes(episodes: List<Int>): ArrayList<Episode> {
            val result = service.getEpisodes(episodes).execute()
            if (result.isSuccessful) {
                return result.body()!!
            } else throw IllegalStateException(result.errorBody()?.string())
        }
    }

}
