package ru.vtinch.rickandmortyexplorer.core

import okio.IOException
import ru.vtinch.rickandmortyexplorer.search.presentation.SearchScreenState

interface HandleError<O : Any> {

    fun handleError(e: Exception): O

    class OnData : HandleError<DomainException> {
        override fun handleError(e: Exception): DomainException {
            throw when (e) {
                is IOException -> DomainException.NoInternetConnection()
                is IllegalStateException -> DomainException.NoData()
                else -> DomainException.ServiceUnavailable()
            }
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

    data class NoData(override val message: String = "There is nothing here") : DomainException()

}