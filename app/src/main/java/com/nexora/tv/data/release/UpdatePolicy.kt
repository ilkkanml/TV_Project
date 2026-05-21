package com.nexora.tv.data.release

object UpdatePolicy {
    const val ENABLE_AUTO_DOWNLOAD = false
    const val ENABLE_FORCE_UPDATE = false
    const val ENABLE_UPDATE_POPUP = false

    val apkFileName: String
        get() = ReleaseConstants.APK_FILE_NAME

    val apkDownloadUrl: String
        get() = ReleaseConstants.APK_DOWNLOAD_URL

    val currentVersionName: String
        get() = ReleaseConstants.appVersionName

    val currentVersionCode: Int
        get() = ReleaseConstants.appVersionCode
}

data class UpdateInfo(
    val latestVersionName: String = "",
    val latestVersionCode: Int = 0,
    val apkUrl: String = UpdatePolicy.apkDownloadUrl,
    val forceUpdate: Boolean = false,
    val message: String = ""
) {
    val hasNewerVersion: Boolean
        get() = latestVersionCode > UpdatePolicy.currentVersionCode
}
