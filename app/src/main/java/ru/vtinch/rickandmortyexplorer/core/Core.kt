package ru.vtinch.rickandmortyexplorer.core

import ru.vtinch.rickandmortyexplorer.detail.presentation.CharacterDetailMapper
import ru.vtinch.rickandmortyexplorer.detail.presentation.CharacterDetailViewModel
import ru.vtinch.rickandmortyexplorer.search.data.cloud.CloudDataSource
import ru.vtinch.rickandmortyexplorer.search.data.cloud.CloudModule
import ru.vtinch.rickandmortyexplorer.search.data.repository.CharacterRepositoryBase
import ru.vtinch.rickandmortyexplorer.search.presentation.CharacterMapper
import ru.vtinch.rickandmortyexplorer.search.presentation.SearchViewModel

class Core : ProvideViewModel {

    val cloudModule = CloudModule.Base(API_URL)
    private val service =cloudModule.service()
    private val cloudDataSource = CloudDataSource.Base(service)
    private val repositoryBase = CharacterRepositoryBase(
        cloudDataSource,
        handleError = HandleError.OnData()
    )

    private val searchViewModel by lazy {
        SearchViewModel(
            characterRepository = repositoryBase, mapper = CharacterMapper(),
            handleError = HandleError.OnSearchScreen()
        )
    }
    private val characterDetailViewModel
        get() =
            CharacterDetailViewModel(repository = repositoryBase, mapper = CharacterDetailMapper())


    override fun <T : MyViewModel> viewModel(clazz: Class<T>): T {
        return when (clazz) {
            SearchViewModel::class.java -> searchViewModel
            CharacterDetailViewModel::class.java -> characterDetailViewModel
            else -> throw IllegalStateException()
        } as T
    }

    companion object{
        private const val API_URL = "https://rickandmortyapi.com/api/"
    }
}
