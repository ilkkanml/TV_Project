package com.nexora.tv.data.playlist

/**
 * M7 local/mock-safe repository direction.
 *
 * This repository does not persist sensitive playlist values.
 * It keeps only safe shell summaries in memory for the current Compose session.
 * Future encrypted local storage requires a separate approved task.
 */
class LocalProfileRepository {
    private var nextProfileNumber = 1

    fun upsertShell(
        currentProfiles: List<SavedLocalProfileShell>,
        editingProfileId: String?,
        draft: PlaylistProfileDraft
    ): List<SavedLocalProfileShell> {
        val existingProfile = currentProfiles.firstOrNull { it.id == editingProfileId }
        val profileId = editingProfileId ?: "local-profile-${nextProfileNumber++}"
        val savedShell = draft.toSavedLocalProfileShell(
            id = profileId,
            isActive = existingProfile?.isActive ?: currentProfiles.none { it.isActive }
        )

        val nextProfiles = if (editingProfileId == null) {
            currentProfiles + savedShell
        } else {
            currentProfiles.map { profile ->
                if (profile.id == editingProfileId) savedShell else profile
            }
        }

        return ensureActiveProfile(nextProfiles)
    }

    fun selectActive(
        currentProfiles: List<SavedLocalProfileShell>,
        profileId: String
    ): List<SavedLocalProfileShell> = currentProfiles.map { profile ->
        profile.copy(isActive = profile.id == profileId)
    }

    fun deleteShell(
        currentProfiles: List<SavedLocalProfileShell>,
        profileId: String
    ): List<SavedLocalProfileShell> {
        val filteredProfiles = currentProfiles.filterNot { it.id == profileId }
        return ensureActiveProfile(filteredProfiles)
    }

    private fun ensureActiveProfile(
        profiles: List<SavedLocalProfileShell>
    ): List<SavedLocalProfileShell> {
        if (profiles.isEmpty()) return emptyList()
        if (profiles.any { it.isActive }) return profiles

        val firstProfileId = profiles.first().id
        return profiles.map { profile ->
            profile.copy(isActive = profile.id == firstProfileId)
        }
    }
}

data class SavedLocalProfileShell(
    val id: String,
    val profileName: String,
    val sourceType: PlaylistSourceType,
    val sourceSummary: String,
    val isActive: Boolean
)

private fun PlaylistProfileDraft.toSavedLocalProfileShell(
    id: String,
    isActive: Boolean
): SavedLocalProfileShell = SavedLocalProfileShell(
    id = id,
    profileName = profileName.trim(),
    sourceType = sourceType,
    sourceSummary = safeSourceSummary(),
    isActive = isActive
)

private fun PlaylistProfileDraft.safeSourceSummary(): String = when (sourceType) {
    PlaylistSourceType.M3uUrl -> {
        val protocol = if (m3uUrl.trim().lowercase().startsWith("https://")) "HTTPS" else "HTTP"
        "$protocol M3U shell — URL hidden"
    }

    PlaylistSourceType.XtreamCodes -> {
        val protocol = if (xtreamServerUrl.trim().lowercase().startsWith("https://")) "HTTPS" else "HTTP"
        "$protocol Xtream shell — credentials hidden"
    }
}
