package com.nexora.tv.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.floor
import kotlin.math.sin

@Composable
fun NexoraCinematicBackdrop(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawSky()
            drawPlanets()
            drawMountains()
            drawVignette()
            drawGrain()
        }
        content()
    }
}

private fun DrawScope.drawSky() {
    val w = size.width
    val h = size.height

    drawRect(color = Color(0xFF02030A))

    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFF172034).copy(alpha = 0.72f),
                Color(0xFF070A13).copy(alpha = 0.94f),
                Color(0xFF02030A)
            ),
            center = Offset(w * 0.52f, h * 0.42f),
            radius = w * 0.78f
        )
    )

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFFFFD38A).copy(alpha = 0.28f),
                Color(0xFF5B6476).copy(alpha = 0.16f),
                Color.Transparent
            ),
            center = Offset(w * 0.50f, h * 0.56f),
            radius = w * 0.18f
        ),
        radius = w * 0.18f,
        center = Offset(w * 0.50f, h * 0.56f)
    )

    for (i in 0 until 70) {
        drawCircle(
            color = Color.White.copy(alpha = 0.035f + pseudo(i * 2.41f) * 0.055f),
            radius = 0.8f + pseudo(i * 4.9f) * 1.15f,
            center = Offset(pseudo(i * 11.17f) * w, pseudo(i * 37.51f) * h * 0.46f)
        )
    }
}

private fun DrawScope.drawPlanets() {
    val w = size.width
    val h = size.height
    val planetCenter = Offset(w * 0.82f, h * 0.22f)
    val planetRadius = h * 0.105f

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFFAAB4C4).copy(alpha = 0.40f),
                Color(0xFF3E4A5E).copy(alpha = 0.26f),
                Color(0xFF07101E).copy(alpha = 0.08f),
                Color.Transparent
            ),
            center = planetCenter.copy(x = planetCenter.x - planetRadius * 0.20f),
            radius = planetRadius
        ),
        radius = planetRadius,
        center = planetCenter
    )

    drawCircle(
        color = Color(0xFFADB4C3).copy(alpha = 0.24f),
        radius = h * 0.025f,
        center = Offset(w * 0.91f, h * 0.29f)
    )
}

private fun DrawScope.drawMountains() {
    val w = size.width
    val h = size.height

    val far = Path().apply {
        moveTo(0f, h * 0.74f)
        lineTo(w * 0.10f, h * 0.66f)
        lineTo(w * 0.18f, h * 0.69f)
        lineTo(w * 0.28f, h * 0.60f)
        lineTo(w * 0.37f, h * 0.67f)
        lineTo(w * 0.50f, h * 0.50f)
        lineTo(w * 0.61f, h * 0.66f)
        lineTo(w * 0.72f, h * 0.61f)
        lineTo(w * 0.84f, h * 0.70f)
        lineTo(w, h * 0.62f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }

    drawPath(
        far,
        Brush.verticalGradient(
            colors = listOf(Color(0xFF1A2433).copy(alpha = 0.76f), Color(0xFF05070D)),
            startY = h * 0.48f,
            endY = h
        )
    )

    val near = Path().apply {
        moveTo(0f, h * 0.83f)
        lineTo(w * 0.12f, h * 0.77f)
        lineTo(w * 0.22f, h * 0.82f)
        lineTo(w * 0.34f, h * 0.73f)
        lineTo(w * 0.48f, h * 0.79f)
        lineTo(w * 0.58f, h * 0.70f)
        lineTo(w * 0.70f, h * 0.81f)
        lineTo(w * 0.84f, h * 0.75f)
        lineTo(w, h * 0.84f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
    }

    drawPath(
        near,
        Brush.verticalGradient(
            colors = listOf(Color(0xFF0B111B).copy(alpha = 0.96f), Color.Black),
            startY = h * 0.68f,
            endY = h
        )
    )
}

private fun DrawScope.drawVignette() {
    val w = size.width
    val h = size.height

    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.38f), Color.Black.copy(alpha = 0.90f)),
            center = Offset(w * 0.50f, h * 0.44f),
            radius = w * 0.74f
        )
    )

    drawRect(
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Black.copy(alpha = 0.90f), Color.Black.copy(alpha = 0.20f), Color.Transparent, Color.Black.copy(alpha = 0.36f))
        )
    )
}

private fun DrawScope.drawGrain() {
    val w = size.width
    val h = size.height
    for (i in 0 until 140) {
        drawCircle(
            color = Color.White.copy(alpha = 0.012f),
            radius = 0.8f,
            center = Offset(pseudo(i * 19.71f) * w, pseudo(i * 7.33f) * h)
        )
    }
}

private fun pseudo(seed: Float): Float {
    val v = sin(seed * 12.9898f) * 43758.5453f
    return v - floor(v)
}
