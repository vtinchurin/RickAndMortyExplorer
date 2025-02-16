package ru.vtinch.rickandmortyexplorer.search.domain.repository

import ru.vtinch.rickandmortyexplorer.search.domain.model.CharacterDetail

interface GetCharacterDetail {

    fun characterDetail(): CharacterDetail
}