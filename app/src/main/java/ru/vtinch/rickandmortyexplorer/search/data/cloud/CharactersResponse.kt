package ru.vtinch.rickandmortyexplorer.search.data.cloud

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val characters: List<CharacterDto>
)