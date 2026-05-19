package com.nexora.tv.ui.intro

import android.graphics.BlurMaskFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val INTRO_DURATION = 9.75f

@Composable
fun NexoraBrandIntroSplash(
    onFinished: () -> Unit
) {
    val elapsed = remember { mutableFloatStateOf(0f) }
    val sfx = remember { NexoraIntroSfx() }

    DisposableEffect(Unit) {
        onDispose {
            sfx.release()
        }
    }

    LaunchedEffect(Unit) {
        var startNanos = 0L
        var lastTime = 0f

        androidx.compose.runtime.withFrameNanos {
            startNanos = it
        }

        sfx.playOpeningDarkPulse()

        while (elapsed.floatValue < INTRO_DURATION) {
            androidx.compose.runtime.withFrameNanos { frame ->
                val now = ((frame - startNanos) / 1_000_000_000f)
                    .coerceIn(0f, INTRO_DURATION)

                if (lastTime < 0.78f && now >= 0.78f) {
                    sfx.playCenterLineGrow()
                }

                if (lastTime < 1.72f && now >= 1.72f) {
                    sfx.playLineSplit()
                }

                if (lastTime < 2.72f && now >= 2.72f) {
                    sfx.playNeonIgnition()
                    sfx.playNeonBreathPad()
                }

                if (lastTime < 5.85f && now >= 5.85f) {
                    sfx.playLineMerge()
                }

                if (lastTime < 6.85f && now >= 6.85f) {
                    sfx.playMountainSweep()
                }

                if (lastTime < 8.82f && now >= 8.82f) {
                    sfx.playPowerDown()
                }

                if (lastTime < 9.45f && now >= 9.45f) {
                    sfx.playFinalTing()
                }

                elapsed.floatValue = now
                lastTime = now
            }
        }

        delay(220)
        onFinished()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF02040D))
    ) {
        drawNexoraIntroFrame(elapsed.floatValue)
    }
}

private fun DrawScope.drawNexoraIntroFrame(t: Float) {
    val w = size.width
    val h = size.height
    val cx = w / 2f

    val logoTextSize = min(w * 0.102f, h * 0.148f)
    val logoWidth = measureNexoraLogoWidth(logoTextSize)
    val baseLogoCenterY = h * 0.405f

    val lineGrow = easeOutCubic(segment(t, 0.78f, 1.72f))

    val splitAmount = when {
        t < 1.72f -> 0f
        t < 2.72f -> easeInOutCubic(segment(t, 1.72f, 2.72f))
        t < 5.85f -> 1f
        t < 6.85f -> 1f - easeInOutCubic(segment(t, 5.85f, 6.85f))
        else -> 0f
    }

    val sideLoopProgress = easeInOutCubic(segment(t, 6.85f, 7.55f))

    val logoLift = when {
        t < 1.72f -> 0f
        t < 6.85f -> splitAmount * logoTextSize * 0.10f
        t < 7.55f -> lerp(
            logoTextSize * 0.10f,
            logoTextSize * 0.20f,
            sideLoopProgress
        )
        else -> logoTextSize * 0.20f
    }

    val logoCenterY = baseLogoCenterY - logoLift

    val baseLineY = logoCenterY + logoTextSize * 0.90f
    val splitGap = logoTextSize * 0.38f

    val topLineY = baseLineY - splitGap * splitAmount
    val bottomLineY = baseLineY + splitGap * splitAmount

    val mountainPhase = easeOutQuint(segment(t, 7.55f, 8.85f))
    val extinguish = easeInOutCubic(segment(t, 8.82f, 9.55f))
    val logoAppear = easeOutCubic(segment(t, 0.15f, 0.95f))

    drawCinematicBackground(t)

    drawNexoraLogo(
        center = Offset(cx, logoCenterY),
        textSize = logoTextSize,
        alpha = logoAppear,
        shimmer = segment(t, 0.55f, 1.55f)
    )

    val halfLine = logoWidth * 0.57f * lineGrow

    if (lineGrow > 0.001f && t < 6.85f) {
        if (splitAmount < 0.025f) {
            drawNeonLine(
                start = Offset(cx - halfLine, baseLineY),
                end = Offset(cx + halfLine, baseLineY),
                stroke = h * 0.0048f,
                alpha = 0.95f,
                time = t
            )
        } else {
            drawNeonLine(
                start = Offset(cx - halfLine, topLineY),
                end = Offset(cx + halfLine, topLineY),
                stroke = h * 0.0046f,
                alpha = 0.95f,
                time = t + 0.15f
            )

            drawNeonLine(
                start = Offset(cx - halfLine, bottomLineY),
                end = Offset(cx + halfLine, bottomLineY),
                stroke = h * 0.0046f,
                alpha = 0.95f,
                time = t + 0.35f
            )
        }
    }

    val playerTextAlpha =
        easeOutCubic(segment(t, 2.55f, 3.25f)) *
            (1f - easeInOutCubic(segment(t, 5.55f, 6.05f)))

    if (playerTextAlpha > 0.01f) {
        drawPlayerEcosystemsText(
            center = Offset(cx, (topLineY + bottomLineY) / 2f),
            textSize = min(w * 0.036f, h * 0.062f),
            alpha = playerTextAlpha,
            breath = 0.72f + 0.28f * sin(t * PI.toFloat() * 1.72f).coerceIn(-1f, 1f),
            time = t
        )
    }

    val mountainAlpha = (1f - extinguish).coerceIn(0f, 1f)

    if (t >= 6.85f && mountainAlpha > 0.001f) {
        drawSideLoopToMountain(
            centerX = cx,
            logoCenterY = logoCenterY,
            logoWidth = logoWidth,
            lineY = baseLineY,
            textSize = logoTextSize,
            loopProgress = segment(t, 6.85f, 7.55f),
            mountainProgress = mountainPhase,
            visibleFraction = mountainAlpha,
            stroke = h * 0.0046f,
            time = t
        )
    }

    if (t > 9.28f) {
        val finalFlash = 1f - segment(t, 9.28f, 9.7f)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFD7FFF4).copy(alpha = 0.30f * finalFlash),
                    Color(0xFF7C3AED).copy(alpha = 0.10f * finalFlash),
                    Color.Transparent
                ),
                center = Offset(cx, logoCenterY - logoTextSize * 1.02f),
                radius = logoTextSize * 0.95f
            ),
            radius = logoTextSize * 0.95f,
            center = Offset(cx, logoCenterY - logoTextSize * 1.02f)
        )
    }
}

private fun DrawScope.drawSideLoopToMountain(
    centerX: Float,
    logoCenterY: Float,
    logoWidth: Float,
    lineY: Float,
    textSize: Float,
    loopProgress: Float,
    mountainProgress: Float,
    visibleFraction: Float,
    stroke: Float,
    time: Float
) {
    val loopEased = easeInOutCubic(loopProgress)

    val lineRightX = centerX + logoWidth * 0.57f
    val sideOutsideX = centerX + logoWidth * 0.83f

    val silhouetteBaseY = logoCenterY - textSize * 0.92f
    val peakY = silhouetteBaseY - textSize * 0.42f

    /*
     * İstenen hareket:
     * Alt çizgi kapandıktan sonra sadece sağ taraftan dolanarak yukarı çıkar.
     * Sağ-sol iki ayrı yarım daire yok.
     * Tek ışık hattı sağ dıştan yukarı çıkar ve NEXORA üstünde silüete bağlanır.
     */
    if (loopEased > 0.001f) {
        val loopPoints = cubicBezierPoints(
            start = Offset(lineRightX, lineY),
            control1 = Offset(sideOutsideX, lineY - textSize * 0.08f),
            control2 = Offset(sideOutsideX, silhouetteBaseY - textSize * 0.24f),
            end = Offset(centerX + logoWidth * 0.56f, silhouetteBaseY),
            steps = 34
        )

        drawPartialGlowPolyline(
            points = loopPoints,
            visibleFraction = loopEased,
            stroke = stroke,
            time = time
        )
    }

    /*
     * Silüet sağdan sola doğru oluşur.
     * Böylece yandan dolanan çizgi, logonun üstünde dağ formuna dönüşür.
     */
    if (mountainProgress > 0.001f) {
        val points = listOf(
            Offset(centerX + logoWidth * 0.56f, silhouetteBaseY),
            Offset(centerX + logoWidth * 0.34f, silhouetteBaseY - textSize * 0.16f),
            Offset(centerX + logoWidth * 0.18f, silhouetteBaseY - textSize * 0.08f),
            Offset(centerX + logoWidth * 0.02f, peakY),
            Offset(centerX - logoWidth * 0.14f, silhouetteBaseY - textSize * 0.10f),
            Offset(centerX - logoWidth * 0.32f, silhouetteBaseY - textSize * 0.18f),
            Offset(centerX - logoWidth * 0.56f, silhouetteBaseY)
        )

        drawPartialGlowPolyline(
            points = points,
            visibleFraction = (mountainProgress * visibleFraction).coerceIn(0f, 1f),
            stroke = stroke,
            time = time + 0.25f
        )
    }
}

private fun DrawScope.drawCinematicBackground(t: Float) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFF111536),
                Color(0xFF050713),
                Color(0xFF02030A)
            ),
            center = Offset(cx, cy * 0.86f),
            radius = w * 0.72f
        )
    )

    val pulse = 0.55f + 0.45f * sin(t * 1.1f)

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFF7C3AED).copy(alpha = 0.22f * pulse),
                Color(0xFF2563EB).copy(alpha = 0.08f * pulse),
                Color.Transparent
            ),
            center = Offset(cx, h * 0.58f),
            radius = w * 0.42f
        ),
        radius = w * 0.42f,
        center = Offset(cx, h * 0.58f)
    )

    for (i in 0 until 95) {
        val px = pseudoRandom(i * 12.8f) * w
        val py = pseudoRandom(i * 43.2f) * h
        val r = 0.7f + pseudoRandom(i * 4.6f) * 1.9f
        val twinkle = 0.25f + 0.75f * abs(sin(t * 0.9f + i))

        drawCircle(
            color = Color(0xFFC4B5FD).copy(alpha = 0.05f * twinkle),
            radius = r,
            center = Offset(px, py)
        )
    }
}

private fun DrawScope.drawNexoraLogo(
    center: Offset,
    textSize: Float,
    alpha: Float,
    shimmer: Float
) {
    drawIntoCanvas { canvas ->
        val native = canvas.nativeCanvas

        val letters = "NEXORA"
        val spacing = textSize * 0.045f

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
            this.textSize = textSize
            textAlign = Paint.Align.LEFT
        }

        val totalWidth = measureNexoraLogoWidth(textSize)
        var x = center.x - totalWidth / 2f
        val baseline = center.y - (paint.ascent() + paint.descent()) / 2f

        val glowPaint = Paint(paint).apply {
            color = android.graphics.Color.argb(
                (80 * alpha).toInt(),
                147,
                51,
                234
            )
            maskFilter = BlurMaskFilter(
                textSize * 0.10f,
                BlurMaskFilter.Blur.NORMAL
            )
        }

        native.drawText(letters, x, baseline, glowPaint)

        letters.forEachIndexed { index, char ->
            val charString = char.toString()
            val charWidth = paint.measureText(charString)
            val localAlpha = (255 * alpha).toInt().coerceIn(0, 255)

            paint.maskFilter = null

            if (char == 'X') {
                paint.shader = LinearGradient(
                    x,
                    baseline - textSize,
                    x + charWidth,
                    baseline,
                    intArrayOf(
                        android.graphics.Color.rgb(168, 85, 247),
                        android.graphics.Color.rgb(96, 165, 250),
                        android.graphics.Color.rgb(236, 72, 153)
                    ),
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.color = android.graphics.Color.argb(
                    localAlpha,
                    255,
                    255,
                    255
                )
            } else {
                paint.shader = null

                val brightness = if (shimmer > 0f) {
                    val sweep = ((index / 5f) - shimmer)
                        .let { 1f - abs(it * 2.2f) }
                        .coerceIn(0f, 1f)

                    (220 + 35 * sweep).toInt()
                } else {
                    235
                }

                paint.color = android.graphics.Color.argb(
                    localAlpha,
                    brightness,
                    brightness,
                    brightness
                )
            }

            native.drawText(charString, x, baseline, paint)
            x += charWidth + spacing
        }
    }
}

private fun DrawScope.drawPlayerEcosystemsText(
    center: Offset,
    textSize: Float,
    alpha: Float,
    breath: Float,
    time: Float
) {
    drawIntoCanvas { canvas ->
        val native = canvas.nativeCanvas

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            this.textSize = textSize
            textAlign = Paint.Align.LEFT
        }

        val part1 = "PLAYER "
        val part2 = "ECO"
        val part3 = "SYSTEMS"

        val w1 = paint.measureText(part1)
        val w2 = paint.measureText(part2)
        val w3 = paint.measureText(part3)

        val total = w1 + w2 + w3
        val x = center.x - total / 2f
        val baseline = center.y - (paint.ascent() + paint.descent()) / 2f

        val glowRadius = textSize * (0.32f + 0.18f * breath)
        val glowAlpha = (alpha * (0.65f + 0.35f * breath)).coerceIn(0f, 1f)

        val gradient = LinearGradient(
            x - total * 0.25f + time * 40f,
            baseline,
            x + total * 1.25f + time * 40f,
            baseline,
            intArrayOf(
                android.graphics.Color.rgb(236, 72, 153),
                android.graphics.Color.rgb(168, 85, 247),
                android.graphics.Color.rgb(59, 130, 246),
                android.graphics.Color.rgb(34, 211, 238),
                android.graphics.Color.rgb(34, 197, 94),
                android.graphics.Color.rgb(250, 204, 21),
                android.graphics.Color.rgb(249, 115, 22)
            ),
            null,
            Shader.TileMode.MIRROR
        )

        val glowPaint = Paint(paint).apply {
            shader = gradient
            maskFilter = BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.NORMAL)
            this.alpha = (180 * glowAlpha).toInt().coerceIn(0, 255)
        }

        native.drawText(part1 + part2 + part3, x, baseline, glowPaint)

        paint.shader = gradient
        paint.maskFilter = null
        paint.alpha = (255 * alpha).toInt().coerceIn(0, 255)
        native.drawText(part1, x, baseline, paint)

        val ecoGlow = Paint(paint).apply {
            shader = null
            color = android.graphics.Color.argb(
                (210 * glowAlpha).toInt().coerceIn(0, 255),
                34,
                255,
                128
            )
            maskFilter = BlurMaskFilter(
                glowRadius * 0.75f,
                BlurMaskFilter.Blur.NORMAL
            )
        }

        native.drawText(part2, x + w1, baseline, ecoGlow)

        paint.shader = null
        paint.color = android.graphics.Color.argb(
            (255 * alpha).toInt().coerceIn(0, 255),
            49,
            255,
            137
        )
        paint.maskFilter = null

        native.drawText(part2, x + w1, baseline, paint)

        paint.shader = gradient
        paint.alpha = (255 * alpha).toInt().coerceIn(0, 255)

        native.drawText(part3, x + w1 + w2, baseline, paint)
    }
}

private fun DrawScope.drawNeonLine(
    start: Offset,
    end: Offset,
    stroke: Float,
    alpha: Float,
    time: Float
) {
    val glowAlpha = alpha.coerceIn(0f, 1f)

    drawLine(
        color = Color(0xFF7C3AED).copy(alpha = 0.24f * glowAlpha),
        start = start,
        end = end,
        strokeWidth = stroke * 7.2f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color(0xFF2563EB).copy(alpha = 0.16f * glowAlpha),
        start = start,
        end = end,
        strokeWidth = stroke * 11f,
        cap = StrokeCap.Round
    )

    drawLine(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF9333EA),
                Color(0xFF60A5FA),
                Color(0xFF22D3EE),
                Color(0xFF7C3AED),
                Color(0xFFEC4899)
            ),
            start = Offset(start.x + sin(time) * 80f, start.y),
            end = Offset(end.x + sin(time + 1.3f) * 80f, end.y)
        ),
        start = start,
        end = end,
        strokeWidth = stroke,
        cap = StrokeCap.Round,
        alpha = glowAlpha
    )

    drawLine(
        color = Color.White.copy(alpha = 0.72f * glowAlpha),
        start = start,
        end = end,
        strokeWidth = stroke * 0.28f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawPartialGlowPolyline(
    points: List<Offset>,
    visibleFraction: Float,
    stroke: Float,
    time: Float
) {
    if (points.size < 2 || visibleFraction <= 0f) return

    val fraction = visibleFraction.coerceIn(0f, 1f)

    val lengths = points.zipWithNext().map { (a, b) ->
        distance(a, b)
    }

    val total = lengths.sum()
    var remaining = total * fraction

    val visibleSegments = mutableListOf<Pair<Offset, Offset>>()

    for (i in 0 until points.lastIndex) {
        val a = points[i]
        val b = points[i + 1]
        val len = lengths[i]

        if (remaining <= 0f) break

        if (remaining >= len) {
            visibleSegments += a to b
            remaining -= len
        } else {
            val p = remaining / len

            visibleSegments += a to Offset(
                x = lerp(a.x, b.x, p),
                y = lerp(a.y, b.y, p)
            )

            remaining = 0f
        }
    }

    visibleSegments.forEach { (a, b) ->
        drawNeonLine(
            start = a,
            end = b,
            stroke = stroke,
            alpha = fraction,
            time = time
        )
    }
}

private fun cubicBezierPoints(
    start: Offset,
    control1: Offset,
    control2: Offset,
    end: Offset,
    steps: Int
): List<Offset> {
    val safeSteps = steps.coerceAtLeast(2)
    val points = mutableListOf<Offset>()

    for (i in 0..safeSteps) {
        val t = i.toFloat() / safeSteps.toFloat()
        val oneMinusT = 1f - t

        val x =
            oneMinusT.pow(3f) * start.x +
                3f * oneMinusT.pow(2f) * t * control1.x +
                3f * oneMinusT * t.pow(2f) * control2.x +
                t.pow(3f) * end.x

        val y =
            oneMinusT.pow(3f) * start.y +
                3f * oneMinusT.pow(2f) * t * control1.y +
                3f * oneMinusT * t.pow(2f) * control2.y +
                t.pow(3f) * end.y

        points += Offset(x, y)
    }

    return points
}

private fun measureNexoraLogoWidth(textSize: Float): Float {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
        this.textSize = textSize
    }

    val spacing = textSize * 0.045f
    val text = "NEXORA"

    var total = 0f

    text.forEachIndexed { index, c ->
        total += paint.measureText(c.toString())

        if (index != text.lastIndex) {
            total += spacing
        }
    }

    return total
}

private fun segment(t: Float, start: Float, end: Float): Float {
    return ((t - start) / (end - start)).coerceIn(0f, 1f)
}

private fun easeOutCubic(x: Float): Float {
    return 1f - (1f - x).pow(3f)
}

private fun easeInOutCubic(x: Float): Float {
    return if (x < 0.5f) {
        4f * x * x * x
    } else {
        1f - (-2f * x + 2f).pow(3f) / 2f
    }
}

private fun easeOutQuint(x: Float): Float {
    return 1f - (1f - x).pow(5f)
}

private fun lerp(a: Float, b: Float, t: Float): Float {
    return a + (b - a) * t.coerceIn(0f, 1f)
}

private fun distance(a: Offset, b: Offset): Float {
    val dx = b.x - a.x
    val dy = b.y - a.y

    return sqrt(dx * dx + dy * dy)
}

private fun pseudoRandom(seed: Float): Float {
    val x = sin(seed * 12.9898f) * 43758.5453f

    return x - floor(x)
}