@file:OptIn(ExperimentalSharedTransitionApi::class)

package ru.vtinch.rickandmortyexplorer.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import ru.vtinch.rickandmortyexplorer.core.viewModel
import ru.vtinch.rickandmortyexplorer.detail.presentation.CharacterDetailViewModel
import ru.vtinch.rickandmortyexplorer.search.presentation.SearchView
import ru.vtinch.rickandmortyexplorer.search.presentation.SearchViewModel

@Serializable
object SearchScreen : Route {
    @Composable
    override fun Show(
        modifier: Modifier,
        navController: NavHostController,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
    ) {
        val customSaver = object : Saver<MutableState<TextFieldValue>, String> {

            override fun restore(value: String): MutableState<TextFieldValue> {
                return mutableStateOf(TextFieldValue(value))
            }

            override fun SaverScope.save(value: MutableState<TextFieldValue>): String {
                return value.value.text
            }

        }

        val viewModel: SearchViewModel = viewModel()

        val textState = rememberSaveable(saver = customSaver) { mutableStateOf(TextFieldValue("")) }

        Column(modifier, verticalArrangement = Arrangement.Top) {
            Text(
                text = "Characters",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            SearchView(textState) {
                viewModel.search(it.text)
            }
            viewModel.screenState.Render(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
                shareTransitionScope = shareTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                onNavigate = { id ->
                    viewModel.getDetail(id) {
                        navController.navigate(DetailScreen(id))
                    }
                },
                onRetry = {
                    viewModel.search(textState.value.text)
                })
        }
    }
}

@Serializable
data class DetailScreen(val id: Int) : Route {
    @Composable
    override fun Show(
        modifier: Modifier,
        navController: NavHostController,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        val viewModel: CharacterDetailViewModel = viewModel()
        val state = viewModel.state

        Box(modifier) {
            state.Render(Modifier.fillMaxSize(), shareTransitionScope, animatedVisibilityScope)
        }
    }
}


interface Route {
    @Composable
    fun Show(
        modifier: Modifier,
        navController: NavHostController,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
    )
}


