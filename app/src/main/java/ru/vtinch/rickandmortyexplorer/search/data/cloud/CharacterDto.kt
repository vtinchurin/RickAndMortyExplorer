package ru.vtinch.rickandmortyexplorer.search.data.cloud

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("location") val location: Location,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("episode") val episodes: List<String>,
    @SerializedName("url") val url: String
)