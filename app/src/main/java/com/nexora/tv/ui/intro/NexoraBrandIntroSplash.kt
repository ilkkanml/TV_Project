package com.nexora.tv.ui.intro

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private const val INTRO_DURATION = 6.8f

@Composable
fun NexoraBrandIntroSplash(onFinished: () -> Unit) {
    val elapsed = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        var startNanos = 0L
        androidx.compose.runtime.withFrameNanos { startNanos = it }
        while (elapsed.floatValue < INTRO_DURATION) {
            androidx.compose.runtime.withFrameNanos { frame ->
                elapsed.floatValue = ((frame - startNanos) / 1_000_000_000f).coerceIn(0f, INTRO_DURATION)
            }
        }
        delay(120)
        onFinished()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        drawIntroFrame(elapsed.floatValue)
    }
}

private fun DrawScope.drawIntroFrame(t: Float) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val logoSize = min(w * 0.105f, h * 0.15f)
    val logoWidth = logoSize * 4.15f
    val logoY = h * 0.405f
    val lineY = logoY + logoSize * 0.86f
    val fade = 1f - easeInOut(segment(t, 6.25f, 6.70f))

    drawCircle(Color(0xFF111536), radius = w * 0.55f, center = Offset(cx, h * 0.48f), alpha = 0.55f)
    drawLogo(cx, logoY, logoSize, easeOut(segment(t, 0.10f, 0.75f)) * fade)

    val halfLine = logoWidth * 0.58f * easeOut(segment(t, 0.45f, 1.20f))
    val split = when {
        t < 1.20f -> 0f
        t < 2.00f -> easeInOut(segment(t, 1.20f, 2.00f))
        t < 3.55f -> 1f
        t < 4.25f -> 1f - easeInOut(segment(t, 3.55f, 4.25f))
        else -> 0f
    }

    if (halfLine > 2f && t < 4.25f) {
        val gap = logoSize * 0.36f * split
        if (split < 0.03f) {
            drawGlowLine(Offset(cx - halfLine, lineY), Offset(cx + halfLine, lineY), h * 0.0048f, fade)
        } else {
            drawGlowLine(Offset(cx - halfLine, lineY - gap), Offset(cx + halfLine, lineY - gap), h * 0.0048f, fade)
            drawGlowLine(Offset(cx - halfLine, lineY + gap), Offset(cx + halfLine, lineY + gap), h * 0.0048f, fade)
        }
    }

    val subAlpha = easeOut(segment(t, 2.00f, 2.65f)) * (1f - easeInOut(segment(t, 3.30f, 3.80f))) * fade
    if (subAlpha > 0.02f) drawSubtitle(cx, lineY, logoSize * 0.33f, subAlpha)

    val mountain = easeOut(segment(t, 4.25f, 5.85f))
    if (mountain > 0.01f) {
        val baseY = logoY - logoSize * 0.95f
        val peakY = baseY - logoSize * 0.42f
        val points = listOf(
            Offset(cx + logoWidth * 0.52f, baseY),
            Offset(cx + logoWidth * 0.34f, baseY - logoSize * 0.15f),
            Offset(cx + logoWidth * 0.14f, baseY - logoSize * 0.06f),
            Offset(cx, peakY),
            Offset(cx - logoWidth * 0.16f, baseY - logoSize * 0.10f),
            Offset(cx - logoWidth * 0.34f, baseY - logoSize * 0.18f),
            Offset(cx - logoWidth * 0.54f, baseY)
        )
        drawPartialLine(points, mountain * fade, h * 0.0048f)
    }
}

private fun DrawScope.drawLogo(cx: Float, cy: Float, size: Float, alpha: Float) {
    drawIntoCanvas { canvas ->
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
            textSize = size
            textAlign = Paint.Align.LEFT
        }
        val baseline = cy - (paint.ascent() + paint.descent()) / 2f
        val totalWidth = paint.measureText("NEXORA")
        var x = cx - totalWidth / 2f
        x = drawSegment(canvas.nativeCanvas, paint, "NE", x, baseline, alpha, 245, 245, 255)
        x = drawSegment(canvas.nativeCanvas, paint, "X", x, baseline, alpha, 124, 58, 237)
        drawSegment(canvas.nativeCanvas, paint, "ORA", x, baseline, alpha, 245, 245, 255)
    }
}

private fun DrawScope.drawSubtitle(cx: Float, cy: Float, size: Float, alpha: Float) {
    drawIntoCanvas { canvas ->
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
            textSize = size
            textAlign = Paint.Align.LEFT
        }
        val baseline = cy - (paint.ascent() + paint.descent()) / 2f
        val totalWidth = paint.measureText("PLAYER ECOSYSTEM")
        var x = cx - totalWidth / 2f
        x = drawSegment(canvas.nativeCanvas, paint, "PLAYER ", x, baseline, alpha, 220, 232, 240, 190)
        x = drawSegment(canvas.nativeCanvas, paint, "ECO", x, baseline, alpha, 57, 255, 136, 230)
        drawSegment(canvas.nativeCanvas, paint, "SYSTEM", x, baseline, alpha, 220, 232, 240, 190)
    }
}

private fun drawSegment(
    canvas: android.graphics.Canvas,
    paint: Paint,
    text: String,
    x: Float,
    baseline: Float,
    alpha: Float,
    red: Int,
    green: Int,
    blue: Int,
    maxAlpha: Int = 255
): Float {
    paint.color = android.graphics.Color.argb((maxAlpha * alpha).toInt().coerceIn(0, 255), red, green, blue)
    canvas.drawText(text, x, baseline, paint)
    return x + paint.measureText(text)
}

private fun DrawScope.drawGlowLine(a: Offset, b: Offset, stroke: Float, alpha: Float) {
    drawLine(Color(0xFF7C3AED).copy(alpha = 0.28f * alpha), a, b, stroke * 7f, StrokeCap.Round)
    drawLine(Color(0xFF39FF88).copy(alpha = 0.20f * alpha), a, b, stroke * 4f, StrokeCap.Round)
    drawLine(Color.White.copy(alpha = 0.82f * alpha), a, b, stroke, StrokeCap.Round)
}

private fun DrawScope.drawPartialLine(points: List<Offset>, fraction: Float, stroke: Float) {
    if (points.size < 2) return
    val f = fraction.coerceIn(0f, 1f)
    val lengths = points.zipWithNext().map { distance(it.first, it.second) }
    var remaining = lengths.sum() * f
    for (i in 0 until points.lastIndex) {
        if (remaining <= 0f) break
        val a = points[i]
        val b = points[i + 1]
        val len = lengths[i]
        if (remaining >= len) {
            drawGlowLine(a, b, stroke, f)
            remaining -= len
        } else {
            val p = remaining / len
            drawGlowLine(a, Offset(lerp(a.x, b.x, p), lerp(a.y, b.y, p)), stroke, f)
            remaining = 0f
        }
    }
}

private fun segment(t: Float, start: Float, end: Float): Float = ((t - start) / (end - start)).coerceIn(0f, 1f)
private fun easeOut(x: Float): Float = 1f - (1f - x).pow(3f)
private fun easeInOut(x: Float): Float = if (x < 0.5f) 4f * x * x * x else 1f - (-2f * x + 2f).pow(3f) / 2f
private fun lerp(a: Float, b: Float, t: Float): Float = a + (b - a) * t.coerceIn(0f, 1f)
private fun distance(a: Offset, b: Offset): Float {
    val dx = b.x - a.x
    val dy = b.y - a.y
    return sqrt(dx * dx + dy * dy)
}
