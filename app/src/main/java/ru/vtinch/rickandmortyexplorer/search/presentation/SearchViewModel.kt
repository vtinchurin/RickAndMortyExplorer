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
    private val handleError: HandleError<DomainException, SearchScreenState>,
) : MyViewModel.Abstract(runAsync) {

//    private val _stateFlow  = MutableStateFlow<UiState>(UiState.Loading)
//    val stateFlow get() = _stateFlow.asStateFlow()

    var innerState by mutableStateOf<SearchScreenState>(SearchScreenState.Initial)
        private set

    init {
        search("")
    }

    fun getDetail(id: Int, onSuccess: () -> Unit) {
        handleAsync({
            try {
                characterRepository.getCharacterById(id)
                innerState
            } catch (e: DomainException) {
                handleError.handleError(e)
            }
        }) {
            innerState = it
            innerState.navigate(onSuccess)
        }
    }

    fun search(query: String) {
        innerState = innerState.updateState()
        handleAsync({
            try {
                val tmp = characterRepository.search(query)
                val listOfCharacterUi = tmp.map { character ->
                    character.map(mapper)
                }
                innerState.updateState(listOfCharacterUi)
            } catch (e: DomainException) {
                handleError.handleError(e)
            }
        }) { newState ->
            innerState = newState
        }
    }

}



