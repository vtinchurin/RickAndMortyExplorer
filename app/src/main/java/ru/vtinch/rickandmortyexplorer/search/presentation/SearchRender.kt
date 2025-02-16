package ru.vtinch.rickandmortyexplorer.search.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


interface SearchRender<T> {

    @ExperimentalSharedTransitionApi
    @Composable
    fun Render(
        modifier: Modifier,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onNavigate: (Int) -> Unit,
        onRetry: () -> Unit,
    ) = Unit
}
