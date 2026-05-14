package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nexora.tv.ui.theme.NexoraColors

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NexoraColors.Black)
            .padding(48.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {

        Text(
            text = "Continue Watching",
            color = NexoraColors.TextPrimary
        )

        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {

            repeat(5) {
                Card(
                    modifier = Modifier
                        .size(width = 220.dp, height = 320.dp)
                        .focusable()
                ) {
                }
            }
        }
    }
}
