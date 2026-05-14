package com.nexora.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nexora.tv.navigation.NexoraNavHost
import com.nexora.tv.ui.theme.NexoraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NexoraTheme {
                NexoraNavHost()
            }
        }
    }
}
