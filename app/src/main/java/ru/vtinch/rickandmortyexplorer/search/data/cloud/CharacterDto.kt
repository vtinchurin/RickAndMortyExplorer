package ru.vtinch.rickandmortyexplorer.search.data.cloud

import com.google.gson.annotations.SerializedName
import ru.vtinch.rickandmortyexplorer.search.domain.model.Character
import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail

data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    val type: String,
    @SerializedName("gender") val gender: String,
    val origin: Origin,
    @SerializedName("location") val location: Location,
    val created: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("episode") val episodes: List<String>,
    @SerializedName("url") val url: String
)