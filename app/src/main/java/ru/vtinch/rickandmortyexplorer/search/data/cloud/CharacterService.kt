package ru.vtinch.rickandmortyexplorer.search.data.cloud

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    fun getAll(): Call<CharactersResponse>

    @GET("character")
    fun search(
        @Query("name") name: String = ""
    ): Call<CharactersResponse>

    @GET("character/{id}")
    fun getCharacterById(
        @Path("id") id: Int
    ): Call<CharacterDto>

    @GET("episode/{episodes}")
    fun getEpisodes(
        @Path("episodes") episodes: List<Int>
    ): Call<ArrayList<Episode>>
}