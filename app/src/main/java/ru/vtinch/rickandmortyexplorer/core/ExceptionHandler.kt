package ru.vtinch.rickandmortyexplorer.core

import okio.IOException
import ru.vtinch.rickandmortyexplorer.search.presentation.SearchScreenState

interface HandleError<O : Any> {

    fun handleError(e: Exception): O

    class OnData : HandleError<DomainException> {
        override fun handleError(e: Exception): DomainException {
            throw if (e is IOException) DomainException.NoInternetConnection()
            else DomainException.ServiceUnavailable()

        }
    }

    class OnSearchScreen : HandleError<SearchScreenState> {
        override fun handleError(e: Exception): SearchScreenState {
            return SearchScreenState.Error(e.message ?: "Service unavailable")
        }
    }
}

sealed class DomainException : Exception() {

    data class NoInternetConnection(override val message: String = "No internet connection") :
        DomainException()

    data class ServiceUnavailable(override val message: String = "Service unavailable") :
        DomainException()

}