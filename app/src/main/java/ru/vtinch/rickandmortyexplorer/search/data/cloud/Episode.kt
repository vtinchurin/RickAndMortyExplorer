package ru.vtinch.rickandmortyexplorer.search.data.cloud

import com.google.gson.annotations.SerializedName

data class Episode(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    @SerializedName("episode")
    val episode: String,
    val id: Int,
    @SerializedName("name")
    val name: String,
    val url: String
)