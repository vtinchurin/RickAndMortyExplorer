package ru.vtinch.rickandmortyexplorer.search.domain.repository

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail

interface GetCharacterDetail {

    fun characterDetail(): CharacterDetail
}