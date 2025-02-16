package ru.vtinch.rickandmortyexplorer.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface MyViewModel {

    fun <T : Any> handleAsync(background: suspend () -> T, ui: (T) -> Unit)

    abstract class Abstract(protected val runAsync: RunAsync) : MyViewModel {

        protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        override fun <T : Any> handleAsync(background: suspend () -> T, ui: (T) -> Unit) {
            runAsync.handleAsync(viewModelScope, background, ui)
        }
    }
}