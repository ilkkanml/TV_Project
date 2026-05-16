package com.nexora.tv.data.playlist

enum class PlaylistSourceType(
    val label: String,
    val description: String
) {
    M3uUrl(
        label = "M3U URL",
        description = "User-provided playlist URL. No source discovery or bundled content."
    ),
    XtreamCodes(
        label = "Xtream Codes",
        description = "User-provided server, username, and password shell only."
    )
}

enum class PlaylistProfileShellState {
    Empty,
    Invalid,
    Saved
}

data class PlaylistProfileDraft(
    val profileName: String,
    val sourceType: PlaylistSourceType,
    val m3uUrl: String = "",
    val xtreamServerUrl: String = "",
    val xtreamUsername: String = "",
    val xtreamPassword: String = ""
)

data class PlaylistProfileValidationResult(
    val state: PlaylistProfileShellState,
    val title: String,
    val message: String
)

fun PlaylistProfileDraft.validateForLocalShell(): PlaylistProfileValidationResult {
    if (profileName.trim().isBlank()) {
        return PlaylistProfileValidationResult(
            state = PlaylistProfileShellState.Invalid,
            title = "Profile name required",
            message = "Name this profile before saving the local shell."
        )
    }

    return when (sourceType) {
        PlaylistSourceType.M3uUrl -> validateM3uShell()
        PlaylistSourceType.XtreamCodes -> validateXtreamShell()
    }
}

private fun PlaylistProfileDraft.validateM3uShell(): PlaylistProfileValidationResult {
    val normalizedUrl = m3uUrl.trim()

    if (normalizedUrl.isBlank()) {
        return PlaylistProfileValidationResult(
            state = PlaylistProfileShellState.Invalid,
            title = "M3U URL required",
            message = "Enter your legally authorized M3U URL. Nexora does not provide playlist sources."
        )
    }

    if (!normalizedUrl.isHttpUrl()) {
        return PlaylistProfileValidationResult(
            state = PlaylistProfileShellState.Invalid,
            title = "Invalid M3U URL",
            message = "Use a valid http:// or https:// URL owned or authorized by you."
        )
    }

    return PlaylistProfileValidationResult(
        state = PlaylistProfileShellState.Saved,
        title = "Profile shell saved",
        message = "Local mock-safe M3U profile shell saved in memory. No playlist was fetched."
    )
}

private fun PlaylistProfileDraft.validateXtreamShell(): PlaylistProfileValidationResult {
    val normalizedServer = xtreamServerUrl.trim()

    if (normalizedServer.isBlank() || xtreamUsername.trim().isBlank() || xtreamPassword.isBlank()) {
        return PlaylistProfileValidationResult(
            state = PlaylistProfileShellState.Invalid,
            title = "Xtream fields required",
            message = "Server URL, username, and password are required for the local shell."
        )
    }

    if (!normalizedServer.isHttpUrl()) {
        return PlaylistProfileValidationResult(
            state = PlaylistProfileShellState.Invalid,
            title = "Invalid server URL",
            message = "Use a valid http:// or https:// server URL that you are legally authorized to access."
        )
    }

    return PlaylistProfileValidationResult(
        state = PlaylistProfileShellState.Saved,
        title = "Profile shell saved",
        message = "Local mock-safe Xtream profile shell saved in memory. No login or provider connection was made."
    )
}

private fun String.isHttpUrl(): Boolean {
    val normalized = trim().lowercase()
    return normalized.startsWith("https://") || normalized.startsWith("http://")
}
