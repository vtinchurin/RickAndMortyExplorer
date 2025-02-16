package ru.vtinch.rickandmortyexplorer.search.data.cloud

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("episode")
    val episode: String,
    @SerializedName("name")
    val name: String,
)