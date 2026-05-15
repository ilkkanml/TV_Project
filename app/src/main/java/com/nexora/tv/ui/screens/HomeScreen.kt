package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06111D))
            .padding(42.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        StaticMenu()
        StaticContent()
    }
}

@Composable
private fun StaticMenu() {
    Column(
        modifier = Modifier.width(180.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "NEXORA",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Premium TV",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        StaticMenuItem("Home", true)
        StaticMenuItem("Live TV", false)
        StaticMenuItem("Movies", false)
        StaticMenuItem("Series", false)
        StaticMenuItem("Settings", false)
    }
}

@Composable
private fun StaticMenuItem(title: String, selected: Boolean) {
    Box(
        modifier = Modifier
            .width(180.dp)
            .height(50.dp)
            .background(
                if (selected) Color(0x3329E7FF) else Color(0xFF141821),
                RoundedCornerShape(18.dp)
            )
            .border(
                1.dp,
                if (selected) Color(0xFF00E5FF) else Color(0x22FFFFFF),
                RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = if (selected) Color.White else Color.LightGray,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun StaticContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        StaticHeader()

        Text(
            text = "Featured Library",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            StaticPoster("Featured", "Main library highlight", Color(0xFF00E5FF))
            StaticPoster("Movies", "Continue watching belongs here", Color(0xFF7C4DFF))
            StaticPoster("Series", "Episode progress belongs here", Color(0xFF00BFA5))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            StaticStatusTile("Loading", "Visible")
            StaticStatusTile("Empty", "Visible")
            StaticStatusTile("Error", "Visible")
        }
    }
}

@Composable
private fun StaticHeader() {
    Column(
        modifier = Modifier
            .width(850.dp)
            .height(154.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, Color(0xFF00E5FF), RoundedCornerShape(28.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Home",
            color = Color(0xFF00E5FF),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Nexora TV Home",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Static safe layout test. No focus, no click, no player route.",
            color = Color.LightGray,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun StaticPoster(title: String, subtitle: String, accent: Color) {
    Box(
        modifier = Modifier
            .size(width = 220.dp, height = 320.dp)
            .background(Color(0xFF151A24), RoundedCornerShape(24.dp))
            .border(2.dp, accent, RoundedCornerShape(24.dp))
            .padding(18.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = Color.LightGray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun StaticStatusTile(title: String, body: String) {
    Column(
        modifier = Modifier
            .width(210.dp)
            .height(74.dp)
            .background(Color(0xFF111722), RoundedCornerShape(20.dp))
            .border(1.dp, Color(0x22FFFFFF), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = body,
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}
