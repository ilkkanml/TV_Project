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

private const val INTRO_DURATION = 7.95f

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

                if (lastTime < 1.42f && now >= 1.42f) {
                    sfx.playLineSplit()
                }

                if (lastTime < 2.18f && now >= 2.18f) {
                    sfx.playNeonIgnition()
                    sfx.playNeonBreathPad()
                }

                if (lastTime < 4.62f && now >= 4.62f) {
                    sfx.playLineMerge()
                }

                if (lastTime < 5.55f && now >= 5.55f) {
                    sfx.playMountainSweep()
                }

                if (lastTime < 7.02f && now >= 7.02f) {
                    sfx.playPowerDown()
                }

                if (lastTime < 7.55f && now >= 7.55f) {
                    sfx.playFinalTing()
                }

                elapsed.floatValue = now
                lastTime = now
            }
        }

        delay(180)
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
    val cy = h / 2f

    val logoTextSize = min(w * 0.112f, h * 0.155f)
    val logoWidth = measureNexoraLogoWidth(logoTextSize)
    val logoCenterY = h * 0.455f
    val xCenter = calculateNexoraXCenter(cx, logoTextSize)

    val logoReveal = easeOutCubic(segment(t, 2.08f, 3.02f))
    val logoSettle = easeOutCubic(segment(t, 3.05f, 4.15f))
    val finalClose = easeInOutCubic(segment(t, 7.05f, 7.72f))
    val logoAlpha = (logoReveal * (1f - finalClose)).coerceIn(0f, 1f)
    val logoY = logoCenterY + (1f - logoSettle) * logoTextSize * 0.055f

    drawCinematicSpaceBackground(t)

    drawPurpleHorizonBirth(
        center = Offset(cx, h * 0.655f),
        progress = segment(t, 0.05f, 1.22f),
        alpha = 1f - segment(t, 2.25f, 3.25f)
    )

    drawConvergingLightStreaks(
        center = Offset(cx, h * 0.335f),
        progress = segment(t, 0.95f, 2.20f),
        alpha = (1f - segment(t, 2.55f, 3.40f)).coerceIn(0f, 1f),
        time = t
    )

    drawIconicXReveal(
        center = Offset(cx, h * 0.335f),
        size = logoTextSize * 2.22f,
        progress = segment(t, 1.25f, 2.42f),
        alpha = (1f - segment(t, 2.65f, 3.35f)).coerceIn(0f, 1f),
        time = t
    )

    drawLogoEnergyTrails(
        center = Offset(cx, logoY),
        logoWidth = logoWidth,
        textSize = logoTextSize,
        progress = segment(t, 2.35f, 3.35f),
        alpha = (1f - segment(t, 4.85f, 5.60f)).coerceIn(0f, 1f),
        time = t
    )

    if (logoAlpha > 0.001f) {
        drawNexoraLogo(
            center = Offset(cx, logoY),
            textSize = logoTextSize,
            alpha = logoAlpha,
            shimmer = segment(t, 2.40f, 3.45f)
        )
    }

    drawPremiumLockupGlow(
        center = Offset(cx, logoY + logoTextSize * 0.82f),
        width = logoWidth * 0.76f,
        progress = segment(t, 3.10f, 3.92f),
        alpha = ((1f - finalClose) * (0.25f + 0.75f * segment(t, 3.10f, 3.92f))).coerceIn(0f, 1f),
        time = t
    )

    val breatheIn = easeOutCubic(segment(t, 5.58f, 5.95f))
    val breatheOut = 1f - easeInOutCubic(segment(t, 7.10f, 7.72f))
    val xBreatheAlpha = (breatheIn * breatheOut).coerceIn(0f, 1f)

    if (xBreatheAlpha > 0.001f || finalClose > 0.001f) {
        drawCenterXBreatheAndClose(
            center = Offset(xCenter, logoY),
            textSize = logoTextSize,
            alpha = xBreatheAlpha,
            close = finalClose,
            time = t
        )
    }

    if (finalClose > 0.001f) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFD8B4FE).copy(alpha = 0.24f * (1f - finalClose)),
                    Color(0xFF60A5FA).copy(alpha = 0.12f * (1f - finalClose)),
                    Color.Transparent
                ),
                center = Offset(xCenter, logoY),
                radius = logoTextSize * (0.65f + finalClose * 0.28f)
            ),
            radius = logoTextSize * (0.65f + finalClose * 0.28f),
            center = Offset(xCenter, logoY)
        )

        drawRect(
            color = Color.Black.copy(alpha = (finalClose * 0.92f).coerceIn(0f, 0.92f))
        )
    }
}

private fun DrawScope.drawCinematicSpaceBackground(t: Float) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFF121638),
                Color(0xFF050713),
                Color(0xFF01020A)
            ),
            center = Offset(cx, cy * 0.82f),
            radius = w * 0.78f
        )
    )

    val pulse = 0.45f + 0.55f * abs(sin(t * 0.52f))

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFF7C3AED).copy(alpha = 0.18f * pulse),
                Color(0xFF2563EB).copy(alpha = 0.075f * pulse),
                Color.Transparent
            ),
            center = Offset(cx, h * 0.57f),
            radius = w * 0.46f
        ),
        radius = w * 0.46f,
        center = Offset(cx, h * 0.57f)
    )

    for (i in 0 until 120) {
        val px = pseudoRandom(i * 12.8f) * w
        val py = pseudoRandom(i * 43.2f) * h
        val r = 0.55f + pseudoRandom(i * 4.6f) * 1.75f
        val twinkle = 0.20f + 0.80f * abs(sin(t * 0.68f + i * 0.31f))

        drawCircle(
            color = Color(0xFFC4B5FD).copy(alpha = 0.045f * twinkle),
            radius = r,
            center = Offset(px, py)
        )
    }
}

private fun DrawScope.drawPurpleHorizonBirth(
    center: Offset,
    progress: Float,
    alpha: Float
) {
    val p = easeOutCubic(progress)
    val a = (alpha * p).coerceIn(0f, 1f)
    if (a <= 0f) return

    val horizonWidth = size.width * (0.16f + 0.58f * p)

    drawLine(
        color = Color(0xFF8B5CF6).copy(alpha = 0.18f * a),
        start = Offset(center.x - horizonWidth, center.y),
        end = Offset(center.x + horizonWidth, center.y),
        strokeWidth = size.height * 0.010f,
        cap = StrokeCap.Round
    )

    drawLine(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color.Transparent,
                Color(0xFFA855F7).copy(alpha = 0.88f * a),
                Color.White.copy(alpha = 0.55f * a),
                Color(0xFF60A5FA).copy(alpha = 0.62f * a),
                Color.Transparent
            )
        ),
        start = Offset(center.x - horizonWidth, center.y),
        end = Offset(center.x + horizonWidth, center.y),
        strokeWidth = size.height * 0.0025f,
        cap = StrokeCap.Round
    )

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.36f * a),
                Color(0xFFA855F7).copy(alpha = 0.24f * a),
                Color.Transparent
            ),
            center = center,
            radius = size.width * 0.10f * p
        ),
        radius = size.width * 0.10f * p,
        center = center
    )
}

private fun DrawScope.drawConvergingLightStreaks(
    center: Offset,
    progress: Float,
    alpha: Float,
    time: Float
) {
    val p = easeOutQuint(progress)
    val a = (alpha * p).coerceIn(0f, 1f)
    if (a <= 0f) return

    val w = size.width
    val h = size.height

    for (i in 0 until 34) {
        val side = if (i % 2 == 0) -1f else 1f
        val y = pseudoRandom(i * 31.7f) * h * 0.78f + h * 0.08f
        val startX = if (side < 0f) {
            -w * 0.10f - pseudoRandom(i * 4.9f) * w * 0.20f
        } else {
            w * 1.10f + pseudoRandom(i * 5.1f) * w * 0.20f
        }
        val targetY = center.y + (y - center.y) * (0.08f + 0.12f * pseudoRandom(i * 2.4f))
        val targetX = center.x + side * logoSafeRandomOffset(i, w) * (1f - p)
        val tailProgress = (p - 0.18f).coerceIn(0f, 1f)
        val head = Offset(lerp(startX, targetX, p), lerp(y, targetY, p))
        val tail = Offset(lerp(startX, targetX, tailProgress), lerp(y, targetY, tailProgress))
        val color = if (side < 0f) Color(0xFFA855F7) else Color(0xFF60A5FA)
        val localAlpha = a * (0.20f + 0.80f * pseudoRandom(i * 8.6f))

        drawColorGlowLine(
            start = tail,
            end = head,
            stroke = h * (0.0015f + pseudoRandom(i * 9.1f) * 0.0022f),
            color = color,
            alpha = localAlpha * (0.72f + 0.28f * abs(sin(time + i)))
        )
    }
}

private fun DrawScope.drawIconicXReveal(
    center: Offset,
    size: Float,
    progress: Float,
    alpha: Float,
    time: Float
) {
    val p = easeInOutCubic(progress)
    val a = (alpha * p).coerceIn(0f, 1f)
    if (a <= 0f) return

    val half = size * 0.50f
    val gap = size * 0.075f * (1f - p)
    val stroke = size * 0.105f
    val pulse = 0.78f + 0.22f * abs(sin(time * 2.1f))

    drawColorGlowLine(
        start = Offset(center.x - half, center.y - half * 0.64f),
        end = Offset(center.x - gap, center.y),
        stroke = stroke,
        color = Color(0xFFA855F7),
        alpha = a * pulse
    )

    drawColorGlowLine(
        start = Offset(center.x - half, center.y + half * 0.64f),
        end = Offset(center.x - gap, center.y),
        stroke = stroke,
        color = Color(0xFF7C3AED),
        alpha = a * pulse
    )

    drawColorGlowLine(
        start = Offset(center.x + half, center.y - half * 0.64f),
        end = Offset(center.x + gap, center.y),
        stroke = stroke,
        color = Color(0xFF60A5FA),
        alpha = a * pulse
    )

    drawColorGlowLine(
        start = Offset(center.x + half, center.y + half * 0.64f),
        end = Offset(center.x + gap, center.y),
        stroke = stroke,
        color = Color(0xFF2563EB),
        alpha = a * pulse
    )

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.30f * a),
                Color(0xFF8B5CF6).copy(alpha = 0.25f * a),
                Color.Transparent
            ),
            center = center,
            radius = size * 0.33f
        ),
        radius = size * 0.33f,
        center = center
    )
}

private fun DrawScope.drawLogoEnergyTrails(
    center: Offset,
    logoWidth: Float,
    textSize: Float,
    progress: Float,
    alpha: Float,
    time: Float
) {
    val p = easeOutQuint(progress)
    val a = (alpha * p).coerceIn(0f, 1f)
    if (a <= 0f) return

    val left = center.x - logoWidth * 0.74f
    val right = center.x + logoWidth * 0.74f
    val upperY = center.y - textSize * 0.33f
    val lowerY = center.y + textSize * 0.48f

    val upper = mutableListOf<Offset>()
    val lower = mutableListOf<Offset>()

    for (i in 0..46) {
        val f = i / 46f
        val x = lerp(left, right, f)
        upper += Offset(
            x = x,
            y = upperY + sin(f * PI.toFloat() * 2.4f + time * 0.65f) * textSize * 0.18f
        )
        lower += Offset(
            x = x,
            y = lowerY + sin(f * PI.toFloat() * 2.1f + time * 0.72f + 1.8f) * textSize * 0.14f
        )
    }

    drawPartialGlowPolyline(
        points = upper,
        visibleFraction = p,
        stroke = size.height * 0.0035f,
        time = time,
        alphaMultiplier = a * 0.76f
    )

    drawPartialGlowPolyline(
        points = lower,
        visibleFraction = p,
        stroke = size.height * 0.0032f,
        time = time + 0.35f,
        alphaMultiplier = a * 0.62f
    )
}

private fun DrawScope.drawPremiumLockupGlow(
    center: Offset,
    width: Float,
    progress: Float,
    alpha: Float,
    time: Float
) {
    val p = easeOutCubic(progress)
    val a = (alpha * p).coerceIn(0f, 1f)
    if (a <= 0f) return

    val half = width * 0.50f * p
    val breathe = 0.78f + 0.22f * abs(sin(time * 1.4f))

    drawLine(
        color = Color(0xFFA855F7).copy(alpha = 0.20f * a * breathe),
        start = Offset(center.x - half, center.y),
        end = Offset(center.x + half, center.y),
        strokeWidth = size.height * 0.018f,
        cap = StrokeCap.Round
    )

    drawLine(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color.Transparent,
                Color(0xFFA855F7).copy(alpha = 0.78f * a),
                Color.White.copy(alpha = 0.55f * a),
                Color(0xFF60A5FA).copy(alpha = 0.45f * a),
                Color.Transparent
            )
        ),
        start = Offset(center.x - half, center.y),
        end = Offset(center.x + half, center.y),
        strokeWidth = size.height * 0.0031f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawCenterXBreatheAndClose(
    center: Offset,
    textSize: Float,
    alpha: Float,
    close: Float,
    time: Float
) {
    val breathing = 0.5f + 0.5f * sin(time * PI.toFloat() * 1.65f)
    val scale = (1f + breathing * 0.085f) * lerp(1f, 0.20f, close)
    val visible = (alpha * (1f - close * 0.65f)).coerceIn(0f, 1f)

    if (visible > 0.001f) {
        drawIntoCanvas { canvas ->
            val native = canvas.nativeCanvas
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
                this.textSize = textSize * scale
                textAlign = Paint.Align.CENTER
            }

            val baseline = center.y - (paint.ascent() + paint.descent()) / 2f
            val gradient = LinearGradient(
                center.x - textSize * 0.45f,
                baseline - textSize,
                center.x + textSize * 0.45f,
                baseline,
                intArrayOf(
                    android.graphics.Color.rgb(236, 72, 153),
                    android.graphics.Color.rgb(168, 85, 247),
                    android.graphics.Color.rgb(96, 165, 250),
                    android.graphics.Color.rgb(255, 255, 255)
                ),
                null,
                Shader.TileMode.CLAMP
            )

            val glowPaint = Paint(paint).apply {
                shader = gradient
                maskFilter = BlurMaskFilter(textSize * (0.18f + breathing * 0.08f), BlurMaskFilter.Blur.NORMAL)
                this.alpha = (210 * visible).toInt().coerceIn(0, 255)
            }

            native.drawText("X", center.x, baseline, glowPaint)

            paint.shader = gradient
            paint.maskFilter = null
            paint.alpha = (255 * visible).toInt().coerceIn(0, 255)
            native.drawText("X", center.x, baseline, paint)
        }
    }

    if (close > 0.001f) {
        val sparkAlpha = (close * (1f - close)).coerceIn(0f, 1f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.48f * sparkAlpha),
                    Color(0xFF60A5FA).copy(alpha = 0.26f * sparkAlpha),
                    Color.Transparent
                ),
                center = center,
                radius = textSize * (0.10f + 0.56f * close)
            ),
            radius = textSize * (0.10f + 0.56f * close),
            center = center
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
        val tvTextSize = textSize * 0.48f
        val mainWidth = measureNexoraMainWidth(textSize)
        val tvWidth = measureNexoraTvWidth(textSize)
        val gap = textSize * 0.22f
        val totalWidth = mainWidth + gap + tvWidth

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
            this.textSize = textSize
            textAlign = Paint.Align.LEFT
        }

        var x = center.x - totalWidth / 2f
        val baseline = center.y - (paint.ascent() + paint.descent()) / 2f

        val glowPaint = Paint(paint).apply {
            color = android.graphics.Color.argb(
                (84 * alpha).toInt().coerceIn(0, 255),
                147,
                51,
                234
            )
            maskFilter = BlurMaskFilter(
                textSize * 0.105f,
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

        val tvPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
            this.textSize = tvTextSize
            textAlign = Paint.Align.LEFT
            shader = LinearGradient(
                x + gap,
                baseline - tvTextSize,
                x + gap + tvWidth,
                baseline,
                intArrayOf(
                    android.graphics.Color.rgb(216, 180, 254),
                    android.graphics.Color.rgb(96, 165, 250),
                    android.graphics.Color.rgb(255, 255, 255)
                ),
                null,
                Shader.TileMode.CLAMP
            )
            alpha = (238 * alpha).toInt().coerceIn(0, 255)
        }

        val tvGlow = Paint(tvPaint).apply {
            maskFilter = BlurMaskFilter(textSize * 0.08f, BlurMaskFilter.Blur.NORMAL)
            alpha = (112 * alpha).toInt().coerceIn(0, 255)
        }

        val tvX = x + gap
        val tvBaseline = baseline - textSize * 0.085f

        native.drawText("TV", tvX, tvBaseline, tvGlow)
        native.drawText("TV", tvX, tvBaseline, tvPaint)
    }
}

private fun DrawScope.drawColorGlowLine(
    start: Offset,
    end: Offset,
    stroke: Float,
    color: Color,
    alpha: Float
) {
    val a = alpha.coerceIn(0f, 1f)
    if (a <= 0f) return

    drawLine(
        color = color.copy(alpha = 0.20f * a),
        start = start,
        end = end,
        strokeWidth = stroke * 5.8f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = color.copy(alpha = 0.14f * a),
        start = start,
        end = end,
        strokeWidth = stroke * 10.5f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = color.copy(alpha = 0.88f * a),
        start = start,
        end = end,
        strokeWidth = stroke,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color.White.copy(alpha = 0.38f * a),
        start = start,
        end = end,
        strokeWidth = stroke * 0.30f,
        cap = StrokeCap.Round
    )
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
        color = Color(0xFF7C3AED).copy(alpha = 0.22f * glowAlpha),
        start = start,
        end = end,
        strokeWidth = stroke * 7.2f,
        cap = StrokeCap.Round
    )

    drawLine(
        color = Color(0xFF2563EB).copy(alpha = 0.14f * glowAlpha),
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
        color = Color.White.copy(alpha = 0.62f * glowAlpha),
        start = start,
        end = end,
        strokeWidth = stroke * 0.24f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawPartialGlowPolyline(
    points: List<Offset>,
    visibleFraction: Float,
    stroke: Float,
    time: Float,
    alphaMultiplier: Float = 1f
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
            alpha = fraction * alphaMultiplier,
            time = time
        )
    }
}

private fun calculateNexoraXCenter(
    logoCenterX: Float,
    textSize: Float
): Float {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
        this.textSize = textSize
    }

    val spacing = textSize * 0.045f
    val totalWidth = measureNexoraLogoWidth(textSize)
    val startX = logoCenterX - totalWidth / 2f
    val prefixWidth = paint.measureText("N") + spacing + paint.measureText("E") + spacing
    val xWidth = paint.measureText("X")

    return startX + prefixWidth + xWidth / 2f
}

private fun measureNexoraMainWidth(textSize: Float): Float {
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

private fun measureNexoraTvWidth(textSize: Float): Float {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
        this.textSize = textSize * 0.48f
    }

    return paint.measureText("TV")
}

private fun measureNexoraLogoWidth(textSize: Float): Float {
    return measureNexoraMainWidth(textSize) + textSize * 0.22f + measureNexoraTvWidth(textSize)
}

private fun logoSafeRandomOffset(index: Int, width: Float): Float {
    return width * (0.02f + pseudoRandom(index * 17.2f) * 0.045f)
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
