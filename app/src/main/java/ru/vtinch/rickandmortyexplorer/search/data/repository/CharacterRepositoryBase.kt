package ru.vtinch.rickandmortyexplorer.search.data.repository

import ru.vtinch.rickandmortyexplorer.core.DomainException
import ru.vtinch.rickandmortyexplorer.core.HandleError
import ru.vtinch.rickandmortyexplorer.core.mapToCharacter
import ru.vtinch.rickandmortyexplorer.core.mapToCharacterDetail
import ru.vtinch.rickandmortyexplorer.search.data.cloud.CharacterDto
import ru.vtinch.rickandmortyexplorer.search.data.cloud.CloudDataSource
import ru.vtinch.rickandmortyexplorer.search.domain.model.Character
import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail
import ru.vtinch.rickandmortyexplorer.search.domain.repository.CharacterRepository

class CharacterRepositoryBase(
    private val cloudDataSource: CloudDataSource,
    private val handleError :HandleError<Exception, DomainException>,
) : CharacterRepository {

    private var currentCharacter: CharacterDetail? = null

    override suspend fun search(query: String): List<Character> {
        try {
            val tmp: List<CharacterDto> = cloudDataSource.fetch(query)
            val result: List<Character> = tmp.mapToCharacter()
            return result
        } catch (e: Exception) {
            throw handleError.handleError(e)
        }
    }

    override suspend fun getCharacterById(id: Int) {
        try {
            val char = cloudDataSource.getCharacterById(id)
            currentCharacter = char.mapToCharacterDetail { list ->
                cloudDataSource.getEpisodes(list).map { episode ->
                    episode.name to episode.episode
                }
            }
        } catch (e: Exception) {
            throw handleError.handleError(e)
        }
    }

    override fun characterDetail(): CharacterDetail = currentCharacter!!


}