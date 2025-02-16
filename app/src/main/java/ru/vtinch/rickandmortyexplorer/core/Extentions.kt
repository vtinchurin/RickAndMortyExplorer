package ru.vtinch.rickandmortyexplorer.core

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ru.vtinch.rickandmortyexplorer.R
import ru.vtinch.rickandmortyexplorer.search.data.cloud.CharacterDto
import ru.vtinch.rickandmortyexplorer.search.domain.model.Character
import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail

fun String.asColor(): Color {
    return when (this.lowercase()) {
        "alive" -> Color.Green
        "dead" -> Color.Red
        else -> Color.Gray
    }
}

@Composable
fun String.ToGenderIcon(modifier: Modifier = Modifier) {
    val resId = when (this.lowercase()) {
        "male" -> R.drawable.ic_male
        "female" -> R.drawable.ic_female
        "genderless" -> R.drawable.ic_genderless
        else -> R.drawable.ic_unknown
    }
    Image(
        painter = painterResource(resId), contentDescription = "", modifier = modifier
    )
}

fun <T : Any> SnapshotStateList<T>.update(new: List<T>) {
    this.clear()
    this.addAll(new)
}

@Composable
inline fun <reified T : MyViewModel> viewModel(): T =
    (LocalContext.current as ProvideViewModel).viewModel(T::class.java)


fun List<CharacterDto>.mapToCharacter(): List<Character> = this.map {
    Character(
        id = it.id, name = it.name, imageUrl = it.imageUrl, status = it.status, species = it.species
    )
}


suspend fun CharacterDto.mapToCharacterDetail(block: suspend (List<Int>) -> List<Pair<String, String>>): CharacterDetail =
    CharacterDetail(
        id = id,
        name = name,
        imageUrl = imageUrl,
        status = status,
        species = species,
        gender = gender,
        location = location.name,
        episodes = block(episodes.map {
            it.split("/").last().toInt()
        })
    )