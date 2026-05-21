package com.nexora.tv.data.device

import android.content.Context
import android.os.Build
import android.provider.Settings
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

object DeviceInstallRegistrar {
    private const val PREFS = "nexora_device_install"
    private const val KEY_INSTALL_ID = "install_id"
    private const val ENDPOINT = "https://www.thenightssecret.com/api/devices/install/index.php"
    private const val APP_VERSION = "0.1.0"

    fun registerAsync(context: Context) {
        val appContext = context.applicationContext
        Thread {
            runCatching { register(appContext) }
        }.start()
    }

    private fun register(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val currentInstallId = prefs.getString(KEY_INSTALL_ID, "").orEmpty()
        val response = postRegistration(
            installId = currentInstallId,
            platformDeviceHash = buildPlatformDeviceHash(context),
            platform = detectPlatform(),
            appVersion = APP_VERSION
        )

        val root = JSONObject(response)
        if (!root.optBoolean("ok", false)) return
        val returnedInstallId = root.optJSONObject("data")?.optString("installId", "").orEmpty()
        if (returnedInstallId.isNotBlank()) {
            prefs.edit().putString(KEY_INSTALL_ID, returnedInstallId).apply()
        }
    }

    private fun postRegistration(
        installId: String,
        platformDeviceHash: String,
        platform: String,
        appVersion: String
    ): String {
        val body = JSONObject()
            .put("installId", installId)
            .put("platformDeviceHash", platformDeviceHash)
            .put("platform", platform)
            .put("appVersion", appVersion)
            .toString()

        val connection = URL(ENDPOINT).openConnection() as HttpURLConnection
        connection.connectTimeout = 10000
        connection.readTimeout = 15000
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "NexoraTV/$APP_VERSION")

        try {
            OutputStreamWriter(connection.outputStream, Charsets.UTF_8).use { writer ->
                writer.write(body)
            }
            val stream = if (connection.responseCode in 200..299) connection.inputStream else connection.errorStream
            return stream?.bufferedReader()?.use { it.readText() }.orEmpty()
        } finally {
            connection.disconnect()
        }
    }

    private fun buildPlatformDeviceHash(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
        val source = listOf(
            context.packageName,
            androidId,
            Build.MANUFACTURER.orEmpty(),
            Build.MODEL.orEmpty(),
            Build.DEVICE.orEmpty()
        ).joinToString("|")
        return sha256(source)
    }

    private fun detectPlatform(): String {
        val maker = Build.MANUFACTURER.orEmpty().lowercase()
        val model = Build.MODEL.orEmpty().lowercase()
        return if (maker.contains("amazon") || model.contains("aft")) "fire_tv" else "android_tv"
    }

    private fun sha256(value: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(value.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { byte -> "%02x".format(byte) }
    }
}
