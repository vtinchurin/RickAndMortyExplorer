package ru.vtinch.rickandmortyexplorer.shared_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.vtinch.rickandmortyexplorer.core.asColor

@Composable
fun Status(status: String, modifier: Modifier = Modifier, size: Dp = 8.dp) {
    Box(
        modifier = modifier.background(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Gray, Color.Transparent
                )
            ),
            shape = CircleShape,
        ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .size(size)
                .background(color = status.asColor(), shape = CircleShape)
        )
    }
}