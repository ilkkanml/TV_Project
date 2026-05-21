package com.nexora.tv.ui.screens

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.M3uParser
import com.nexora.tv.data.live.ProviderPortalLoader
import com.nexora.tv.data.live.RemoteListLoader
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations

internal fun connectCurrentSource(
    context: Context,
    mode: SourceMode,
    profileName: String,
    server: String,
    user: String,
    pass: String,
    listUrl: String,
    localText: String,
    singleUrl: String,
    navController: NavController,
    setLoading: (Boolean) -> Unit,
    setMessage: (String) -> Unit
) {
    val safeName = profileName.ifBlank { AppLanguageStore.t("User Profile", "Kullanıcı Profili") }

    when (mode) {
        SourceMode.Portal -> connectProviderPortal(
            context = context,
            safeName = safeName,
            server = server,
            user = user,
            pass = pass,
            navController = navController,
            setLoading = setLoading,
            setMessage = setMessage
        )

        SourceMode.ListUrl -> connectM3uUrl(
            context = context,
            safeName = safeName,
            listUrl = listUrl,
            navController = navController,
            setLoading = setLoading,
            setMessage = setMessage
        )

        SourceMode.LocalFile -> connectLocalData(
            context = context,
            safeName = safeName,
            localText = localText,
            navController = navController,
            setMessage = setMessage
        )

        SourceMode.Single -> connectSingleStream(
            context = context,
            safeName = safeName,
            singleUrl = singleUrl,
            navController = navController,
            setMessage = setMessage
        )
    }
}

private fun connectProviderPortal(
    context: Context,
    safeName: String,
    server: String,
    user: String,
    pass: String,
    navController: NavController,
    setLoading: (Boolean) -> Unit,
    setMessage: (String) -> Unit
) {
    if (server.isBlank() || user.isBlank() || pass.isBlank()) {
        setMessage(AppLanguageStore.t("Server, user name and password are required.", "Sunucu, kullanıcı adı ve şifre zorunludur."))
        return
    }

    setLoading(true)
    setMessage(AppLanguageStore.t("Connecting...", "Bağlanıyor..."))

    Thread {
        val result = runCatching { ProviderPortalLoader.loadAll(server, user, pass) }
        Handler(Looper.getMainLooper()).post {
            setLoading(false)
            result.onSuccess { loaded ->
                val status = "${loaded.live.size} live • ${loaded.movies.size} movies • ${loaded.series.size} series"
                val account = loaded.account

                LivePlaybackSession.setCatalog(safeName, loaded.live, loaded.movies, loaded.series)
                MediaProfileStore.upsert(
                    profileName = safeName,
                    sourceType = "Provider API",
                    serverAddress = server,
                    accountName = user,
                    accessKey = pass,
                    live = loaded.live,
                    movies = loaded.movies,
                    series = loaded.series,
                    status = status,
                    context = context,
                    mediaAccountExpiry = account.expiryDate,
                    mediaAccountStatus = account.status,
                    activeConnections = account.activeConnections,
                    maxConnections = account.maxConnections,
                    trialStatus = account.trialStatus
                )
                setMessage(status)
                navigateHome(navController)
            }.onFailure { error ->
                setMessage(error.message ?: AppLanguageStore.t("Could not connect.", "Bağlanılamadı."))
            }
        }
    }.start()
}

private fun connectM3uUrl(
    context: Context,
    safeName: String,
    listUrl: String,
    navController: NavController,
    setLoading: (Boolean) -> Unit,
    setMessage: (String) -> Unit
) {
    if (listUrl.isBlank()) {
        setMessage(AppLanguageStore.t("M3U URL is required.", "M3U URL zorunludur."))
        return
    }

    setLoading(true)
    setMessage(AppLanguageStore.t("Loading...", "Yükleniyor..."))

    Thread {
        val result = runCatching { RemoteListLoader.load(listUrl) }
        Handler(Looper.getMainLooper()).post {
            setLoading(false)
            result.onSuccess { loaded ->
                val status = "${loaded.size} live • 0 movies • 0 series"
                LivePlaybackSession.setCatalog(safeName, loaded)
                MediaProfileStore.upsert(safeName, "M3U URL", listUrl, "", "", loaded, emptyList(), emptyList(), status, context)
                setMessage(status)
                if (loaded.isNotEmpty()) navigateHome(navController)
            }.onFailure { error ->
                setMessage(error.message ?: AppLanguageStore.t("Could not load.", "Yüklenemedi."))
            }
        }
    }.start()
}

private fun connectLocalData(
    context: Context,
    safeName: String,
    localText: String,
    navController: NavController,
    setMessage: (String) -> Unit
) {
    if (localText.isBlank()) {
        setMessage(AppLanguageStore.t("Local data is empty.", "Yerel veri boş."))
        return
    }

    val parsed = M3uParser.parse(localText)
    val status = "${parsed.size} live • 0 movies • 0 series"
    LivePlaybackSession.setCatalog(safeName, parsed)
    MediaProfileStore.upsert(safeName, "Local data", "Local", "", "", parsed, emptyList(), emptyList(), status, context)
    setMessage(status)
    if (parsed.isNotEmpty()) navigateHome(navController)
}

private fun connectSingleStream(
    context: Context,
    safeName: String,
    singleUrl: String,
    navController: NavController,
    setMessage: (String) -> Unit
) {
    val stream = singleUrl.trim()
    if (!stream.startsWith("http://") && !stream.startsWith("https://")) {
        setMessage(AppLanguageStore.t("Stream URL must start with http or https.", "Yayın URL http veya https ile başlamalı."))
        return
    }

    val item = LiveChannel("Single Stream", stream, "Single Stream")
    LivePlaybackSession.select(item)
    MediaProfileStore.upsert(safeName, "Single stream", stream, "", "", listOf(item), emptyList(), emptyList(), "1 stream ready", context)
    navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
}

private fun navigateHome(navController: NavController) {
    navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
}
