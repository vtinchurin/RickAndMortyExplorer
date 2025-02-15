package ru.vtinch.rickandmortyexplorer.core

interface ProvideViewModel {
    fun <T : MyViewModel> viewModel(clazz: Class<T>): T
}