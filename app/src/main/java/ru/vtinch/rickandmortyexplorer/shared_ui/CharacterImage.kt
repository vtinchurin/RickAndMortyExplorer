package ru.vtinch.rickandmortyexplorer.shared_ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CharacterImage(imageUrl: String, modifier: Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier.heightIn(250.dp, 350.dp)
    )
}