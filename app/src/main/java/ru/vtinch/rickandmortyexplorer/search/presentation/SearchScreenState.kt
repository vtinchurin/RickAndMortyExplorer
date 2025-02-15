package ru.vtinch.rickandmortyexplorer.search.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vtinch.rickandmortyexplorer.core.update


@OptIn(ExperimentalSharedTransitionApi::class)
interface SearchScreenState : SearchRender<CharacterUi> {

    fun updateState(content: List<CharacterUi>): SearchScreenState {
        return Content(content)
    }

    fun updateState(): SearchScreenState = Loading

    fun navigate(callback: () -> Unit) = callback.invoke()

    data object Initial : SearchScreenState

    data object Loading : SearchScreenState {

        @Composable
        override fun Render(
            modifier: Modifier,
            shareTransitionScope: SharedTransitionScope,
            animatedVisibilityScope: AnimatedVisibilityScope,
            onNavigate: (Int) -> Unit,
            onRetry: () -> Unit,
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier)
            }
        }
    }

    data class Content(private val content: SnapshotStateList<CharacterUi>) : SearchScreenState {

        init {
            Log.d("tvn", "State Content created")
        }

        constructor(content: List<CharacterUi>) : this(SnapshotStateList<CharacterUi>()) {
            this.content.update(content)
        }

        override fun updateState(content: List<CharacterUi>): SearchScreenState {
            this.content.update(content)
            return this

        }


        @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
        @Composable
        override fun Render(
            modifier: Modifier,
            shareTransitionScope: SharedTransitionScope,
            animatedVisibilityScope: AnimatedVisibilityScope,
            onNavigate: (Int) -> Unit,
            onRetry: () -> Unit,
        ) {

            Box(Modifier.fillMaxHeight()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Spacer(
                            modifier = Modifier
                                .height(8.dp)
                                .background(Color.Blue)
                        )
                    }
                    items(content, key = { it.hashCode() }) { characterCard ->
                        characterCard.Render(modifier = Modifier.fillMaxWidth(),
                            shareTransitionScope = shareTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onNavigate = { characterId ->
                                onNavigate(characterId)
                            },
                            onRetry = {})
                    }
                }
            }
        }
    }

    data class Error(private val error: String) : SearchScreenState {

        override fun updateState(content: List<CharacterUi>) = updateState()

        override fun navigate(callback: () -> Unit) = Unit

        @Composable
        override fun Render(
            modifier: Modifier,
            shareTransitionScope: SharedTransitionScope,
            animatedVisibilityScope: AnimatedVisibilityScope,
            onNavigate: (Int) -> Unit,
            onRetry: () -> Unit,
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = onRetry,
                        modifier = Modifier,
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }

    }
}
