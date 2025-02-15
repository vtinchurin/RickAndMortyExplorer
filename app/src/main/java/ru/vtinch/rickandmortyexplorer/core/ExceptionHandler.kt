package ru.vtinch.rickandmortyexplorer.core

import okio.IOException
import ru.vtinch.rickandmortyexplorer.search.presentation.SearchScreenState

interface HandleError<In : Throwable,O: Any> {

    fun handleError(e: In): O

    class OnData : HandleError<Exception, DomainException> {
        override fun handleError(e: Exception): DomainException {
            throw if (e is IOException) DomainException.NoInternetConnection()
            else DomainException.ServiceUnavailable()

        }
    }

    class OnSearchScreen : HandleError<DomainException, SearchScreenState> {
        override fun handleError(e: DomainException): SearchScreenState {
            return SearchScreenState.Error(e.message ?: "Service unavailable")
        }
    }
}

sealed class DomainException:Throwable() {

    data class NoInternetConnection(override val message: String = "No internet connection") :
        DomainException()

    data class ServiceUnavailable(override val message: String = "Service unavailable") :
        DomainException()

}