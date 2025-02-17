package ru.vtinch.rickandmortyexplorer.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.vtinch.rickandmortyexplorer.core.DomainException
import ru.vtinch.rickandmortyexplorer.core.HandleError
import ru.vtinch.rickandmortyexplorer.core.MyViewModel
import ru.vtinch.rickandmortyexplorer.core.RunAsync
import ru.vtinch.rickandmortyexplorer.search.domain.model.Character
import ru.vtinch.rickandmortyexplorer.search.domain.repository.SearchCharacter

class SearchViewModel(
    private val characterRepository: SearchCharacter,
    runAsync: RunAsync = RunAsync.Search(),
    private val mapper: Character.Mapper<CharacterUi>,
    private val handleError: HandleError<SearchScreenState>,
) : MyViewModel.Abstract(runAsync) {

    var screenState by mutableStateOf<SearchScreenState>(SearchScreenState.Initial)
        private set

    init {
        search("")
    }

    fun getDetail(id: Int, onSuccess: () -> Unit) {
        handleAsync({
            try {
                characterRepository.getCharacterById(id)
                screenState
            } catch (e: DomainException) {
                handleError.handleError(e)
            }
        }) {
            screenState = it
            screenState.navigate(onSuccess)
        }
    }

    fun search(query: String) {
        screenState = screenState.updateState()
        handleAsync({
            try {
                val tmp = characterRepository.search(query)
                val listOfCharacterUi = tmp.map { character ->
                    character.map(mapper)
                }
                screenState.updateState(listOfCharacterUi)
            } catch (e: DomainException) {
                handleError.handleError(e)
            }
        }) { newState ->
            screenState = newState
        }
    }

}



