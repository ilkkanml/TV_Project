package com.nexora.tv.data.app

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppLanguageStore {
    enum class Language { TR, EN }

    private const val PREFS = "nexora_app_prefs"
    private const val KEY_LANGUAGE = "language"

    var language by mutableStateOf(Language.EN)
        private set

    var initialized by mutableStateOf(false)
        private set

    var hasChosenLanguage by mutableStateOf(false)
        private set

    fun init(context: Context) {
        if (initialized) return
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val saved = prefs.getString(KEY_LANGUAGE, null)
        hasChosenLanguage = saved != null
        language = if (saved == "TR") Language.TR else Language.EN
        initialized = true
    }

    fun setLanguage(context: Context, value: Language) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANGUAGE, value.name)
            .apply()
        language = value
        hasChosenLanguage = true
        initialized = true
    }

    val isTurkish: Boolean
        get() = language == Language.TR

    fun t(en: String, tr: String): String = if (isTurkish) tr else en
}
