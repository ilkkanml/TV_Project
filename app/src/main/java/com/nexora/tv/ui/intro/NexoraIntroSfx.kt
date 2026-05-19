package com.nexora.tv.ui.intro

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import java.util.concurrent.Executors
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

class NexoraIntroSfx {

    private val executor = Executors.newCachedThreadPool()
    private val sampleRate = 48_000

    fun release() {
        executor.shutdownNow()
    }

    fun playOpeningDarkPulse() {
        executor.execute {
            play(
                synthSweep(
                    durationMs = 900,
                    startHz = 58f,
                    endHz = 92f,
                    volume = 0.26f,
                    noise = 0.015f
                )
            )
        }
    }

    fun playCenterLineGrow() {
        executor.execute {
            play(
                synthSweep(
                    durationMs = 950,
                    startHz = 180f,
                    endHz = 880f,
                    volume = 0.34f,
                    noise = 0.035f
                )
            )
        }
    }

    fun playLineSplit() {
        executor.execute {
            play(
                synthSweep(
                    durationMs = 720,
                    startHz = 520f,
                    endHz = 260f,
                    volume = 0.28f,
                    noise = 0.045f
                )
            )
        }
    }

    fun playNeonIgnition() {
        executor.execute {
            play(
                synthImpact(
                    durationMs = 620,
                    baseHz = 190f,
                    shimmerHz = 1320f,
                    volume = 0.42f
                )
            )
        }
    }

    fun playNeonBreathPad() {
        executor.execute {
            play(
                synthPad(
                    durationMs = 3100,
                    volume = 0.18f
                )
            )
        }
    }

    fun playLineMerge() {
        executor.execute {
            play(
                synthSweep(
                    durationMs = 740,
                    startHz = 760f,
                    endHz = 170f,
                    volume = 0.30f,
                    noise = 0.035f
                )
            )
        }
    }

    fun playMountainSweep() {
        executor.execute {
            play(
                synthSweep(
                    durationMs = 1500,
                    startHz = 260f,
                    endHz = 680f,
                    volume = 0.25f,
                    noise = 0.025f
                )
            )
        }
    }

    fun playPowerDown() {
        executor.execute {
            play(
                synthSweep(
                    durationMs = 760,
                    startHz = 1180f,
                    endHz = 90f,
                    volume = 0.25f,
                    noise = 0.025f
                )
            )
        }
    }

    fun playFinalTing() {
        executor.execute {
            play(
                synthTing(
                    durationMs = 980,
                    volume = 0.34f
                )
            )
        }
    }

    private fun synthSweep(
        durationMs: Int,
        startHz: Float,
        endHz: Float,
        volume: Float,
        noise: Float
    ): ShortArray {
        val count = (sampleRate * durationMs / 1000f).roundToInt()
        val out = ShortArray(count)
        var phase = 0.0

        for (i in 0 until count) {
            val p = i.toFloat() / count.toFloat()
            val freq = startHz + (endHz - startHz) * p
            phase += 2.0 * PI * freq / sampleRate

            val attack = (p / 0.08f).coerceIn(0f, 1f)
            val release = ((1f - p) / 0.18f).coerceIn(0f, 1f)
            val env = attack * release

            val n = (Random.nextFloat() * 2f - 1f) * noise
            val value =
                sin(phase).toFloat() * 0.82f +
                    sin(phase * 2.01).toFloat() * 0.12f +
                    n

            out[i] = toPcm(value * env * volume)
        }

        return out
    }

    private fun synthImpact(
        durationMs: Int,
        baseHz: Float,
        shimmerHz: Float,
        volume: Float
    ): ShortArray {
        val count = (sampleRate * durationMs / 1000f).roundToInt()
        val out = ShortArray(count)

        var basePhase = 0.0
        var shimmerPhase = 0.0

        for (i in 0 until count) {
            val p = i.toFloat() / count.toFloat()

            val baseFreq = baseHz * (1f - p * 0.55f)
            basePhase += 2.0 * PI * baseFreq / sampleRate
            shimmerPhase += 2.0 * PI * shimmerHz / sampleRate

            val env = exp(-4.4f * p)
            val shimmerEnv = exp(-9.2f * p)

            val value =
                sin(basePhase).toFloat() * 0.78f * env +
                    sin(shimmerPhase).toFloat() * 0.33f * shimmerEnv

            out[i] = toPcm(value * volume)
        }

        return out
    }

    private fun synthPad(
        durationMs: Int,
        volume: Float
    ): ShortArray {
        val count = (sampleRate * durationMs / 1000f).roundToInt()
        val out = ShortArray(count)

        var p1 = 0.0
        var p2 = 0.0
        var p3 = 0.0

        for (i in 0 until count) {
            val progress = i.toFloat() / count.toFloat()
            val breath = 0.55f + 0.45f * sin(progress * PI.toFloat() * 6f)

            p1 += 2.0 * PI * 220.0 / sampleRate
            p2 += 2.0 * PI * 330.0 / sampleRate
            p3 += 2.0 * PI * 440.0 / sampleRate

            val attack = (progress / 0.12f).coerceIn(0f, 1f)
            val release = ((1f - progress) / 0.18f).coerceIn(0f, 1f)
            val env = attack * release

            val value =
                sin(p1).toFloat() * 0.34f +
                    sin(p2).toFloat() * 0.24f +
                    sin(p3).toFloat() * 0.16f

            out[i] = toPcm(value * breath * env * volume)
        }

        return out
    }

    private fun synthTing(
        durationMs: Int,
        volume: Float
    ): ShortArray {
        val count = (sampleRate * durationMs / 1000f).roundToInt()
        val out = ShortArray(count)

        var p1 = 0.0
        var p2 = 0.0
        var p3 = 0.0

        for (i in 0 until count) {
            val progress = i.toFloat() / count.toFloat()

            p1 += 2.0 * PI * 1760.0 / sampleRate
            p2 += 2.0 * PI * 2217.0 / sampleRate
            p3 += 2.0 * PI * 2960.0 / sampleRate

            val env = exp(-5.8f * progress)

            val value =
                sin(p1).toFloat() * 0.62f +
                    sin(p2).toFloat() * 0.30f +
                    sin(p3).toFloat() * 0.18f

            out[i] = toPcm(value * env * volume)
        }

        return out
    }

    private fun play(samples: ShortArray) {
        val audioFormat = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val track = AudioTrack.Builder()
            .setAudioAttributes(audioAttributes)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(samples.size * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        try {
            track.write(samples, 0, samples.size)
            track.play()
            Thread.sleep((samples.size * 1000L / sampleRate) + 80L)
        } finally {
            track.release()
        }
    }

    private fun toPcm(value: Float): Short {
        val clamped = value.coerceIn(-1f, 1f)
        return (clamped * Short.MAX_VALUE).roundToInt()
            .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
            .toShort()
    }
}
