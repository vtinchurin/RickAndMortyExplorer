package ru.vtinch.rickandmortyexplorer.search.presentation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.vtinch.rickandmortyexplorer.shared_ui.CharacterImage
import ru.vtinch.rickandmortyexplorer.shared_ui.Status


data class CharacterUi(
    private val id: Int,
    private val name: String,
    private val status: String,
    private val species: String,
    private val imageUrl: String,
) : SearchRender<CharacterUi> {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    override fun Render(
        modifier: Modifier,
        shareTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onNavigate: (Int) -> Unit,
        onRetry: () -> Unit
    ) {
        with(shareTransitionScope) {

            Card(modifier = Modifier
                .padding(horizontal = 8.dp)
                .heightIn(max = 128.dp),
                onClick = { onNavigate.invoke(id) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    CharacterImage(
                        imageUrl,
                        Modifier
                            .size(128.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "image/$imageUrl"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )

                    Column(
                        modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = "Status",
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                                modifier = modifier
                            )
                            Row(
                                modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Status(status, Modifier)
                                Text(text = "$status - $species")
                            }
                        }
                    }
                }
            }
        }
    }
}