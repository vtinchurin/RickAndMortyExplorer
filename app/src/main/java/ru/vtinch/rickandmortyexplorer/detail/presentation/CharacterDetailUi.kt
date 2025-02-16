package ru.vtinch.rickandmortyexplorer.detail.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import ru.vtinch.rickandmortyexplorer.core.ToGenderIcon
import ru.vtinch.rickandmortyexplorer.shared_ui.CharacterImage
import ru.vtinch.rickandmortyexplorer.shared_ui.Status


@Immutable
data class CharacterDetailUi(
    private val id: Int,
    private val name: String,
    private val status: String,
    private val imageUrl: String,
    private val species: String,
    private val gender: String,
    private val location: String,
    private val episodes: List<Episode>
) {
    @ExperimentalSharedTransitionApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Render(
        modifier: Modifier,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
    ) {

        val density = LocalDensity.current
        val imageMinSize = 0.dp
        val imageMaxSize = 345.dp
        var imageSize by remember { mutableStateOf(imageMaxSize) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    fun deltaDp() = with(density) { available.y.toDp() }

                    if (available.y < 0 && imageSize > imageMinSize) {
                        imageSize = max(imageMinSize, imageSize + deltaDp())
                    }

                    if (available.y > 0 && imageSize < imageMaxSize) {
                        imageSize = min(imageMaxSize, imageSize + deltaDp())
                    }
                    return Offset.Zero
                }
            }
        }
        with(shareTransitionScope) {
            Box(
                modifier = modifier.nestedScroll(nestedScrollConnection),
                contentAlignment = Alignment.TopCenter
            ) {
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState, modifier = modifier, verticalArrangement = Arrangement.Top
                ) {
                    stickyHeader {
                        Box {
                            CharacterImage(
                                imageUrl,
                                Modifier
                                    .fillMaxWidth()
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "image/$imageUrl"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
                                    .height(imageSize)
                            )
                        }

                    }

                    stickyHeader {
                        HorizontalDivider(Modifier.fillMaxWidth())
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(0.5f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.displaySmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(0.5f)
                                    )
                                    Status(status, Modifier.padding(16.dp), size = 16.dp)
                                }
                                gender.ToGenderIcon(Modifier.padding(16.dp))
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                PairInfo("Location", location, modifier = Modifier)
                                PairInfo("Species", species)
                            }
                        }
                    }
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background),
                        ) {
                            Column {
                                Text(
                                    text = "Episodes",
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.padding(8.dp)
                                )
                                HorizontalDivider(Modifier)
                            }
                        }
                    }
                    items(episodes.size) {
                        Spacer(Modifier.height(8.dp))
                        episodes[it].Render(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun PairInfo(
    title: String, value: String, modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$title : ",
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Light
        )
    }
}


data class Episode(
    private val name: String,
    private val episode: String,
) {
    @Composable
    fun Render(modifier: Modifier) {
        Card(
            modifier, colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(name, style = MaterialTheme.typography.titleLarge)
                Text(episode, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}