package ru.vtinch.rickandmortyexplorer.detail.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
interface DetailScreenState {


    @Composable
    fun Render(
        modifier: Modifier,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    )

    fun update(content: CharacterDetailUi) =
        Content(content)

    data class Content(private val content: CharacterDetailUi) : DetailScreenState {
        @Composable
        override fun Render(
            modifier: Modifier,
            shareTransitionScope: SharedTransitionScope,
            animatedVisibilityScope: AnimatedVisibilityScope
        ) {
            content.Render(
                modifier = modifier,
                shareTransitionScope = shareTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        }
    }

    data object Loading : DetailScreenState {

        @Composable
        override fun Render(
            modifier: Modifier,
            shareTransitionScope: SharedTransitionScope,
            animatedVisibilityScope: AnimatedVisibilityScope
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier)
            }
        }
    }

}