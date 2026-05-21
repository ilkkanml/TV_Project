package com.nexora.tv.data.release

import com.nexora.tv.BuildConfig

object ReleaseConstants {
    const val APP_NAME = "Nexora TV"
    const val APK_FILE_NAME = "nexoratv.apk"
    const val APK_DOWNLOAD_URL = "https://www.thenightssecret.com/dl/nexoratv.apk"
    const val DEVICE_INSTALL_ENDPOINT = "https://www.thenightssecret.com/api/devices/install/index.php"

    val appVersionName: String
        get() = BuildConfig.VERSION_NAME.ifBlank { "0.1.2" }

    val appVersionCode: Int
        get() = BuildConfig.VERSION_CODE
}
