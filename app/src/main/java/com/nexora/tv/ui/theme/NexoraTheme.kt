package com.nexora.tv.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NexoraScheme = darkColorScheme(
    primary = NexoraColors.Cyan,
    secondary = NexoraColors.Blue,
    background = NexoraColors.Black,
    surface = NexoraColors.Surface,
    onPrimary = NexoraColors.Black,
    onBackground = NexoraColors.TextPrimary,
    onSurface = NexoraColors.TextPrimary
)

@Composable
fun NexoraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NexoraScheme,
        content = content
    )
}
