package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NEXORA TV",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Home runtime diagnostic screen",
            color = Color.LightGray,
            fontSize = 18.sp
        )

        Text(
            text = "If you see this screen, Login → Home navigation works.",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}
