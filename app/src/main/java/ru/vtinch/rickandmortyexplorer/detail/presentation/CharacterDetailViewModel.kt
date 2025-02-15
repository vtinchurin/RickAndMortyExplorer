package ru.vtinch.rickandmortyexplorer.detail.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.vtinch.rickandmortyexplorer.core.MyViewModel
import ru.vtinch.rickandmortyexplorer.core.RunAsync
import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail
import ru.vtinch.rickandmortyexplorer.search.domain.repository.GetCharacterDetail

@OptIn(ExperimentalSharedTransitionApi::class)

class CharacterDetailViewModel(
    private val repository: GetCharacterDetail,
    runAsync: RunAsync = RunAsync.Base(),
    private val mapper: CharacterDetail.Mapper<CharacterDetailUi>
) : MyViewModel.Abstract(runAsync) {

    var state by mutableStateOf<DetailScreenState>(DetailScreenState.Loading)
        private set


    init {
        val char = repository.characterDetail()
        val characterUi = char.map(mapper)
        state = state.update(characterUi)
    }
}