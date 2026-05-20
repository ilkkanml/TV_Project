package com.nexora.tv.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

private val BrandViolet = Color(0xFF7C3AED)
private val BrandGreen = Color(0xFF39FF88)

@Composable
fun NexoraWordmark(
    fontSize: TextUnit,
    letterSpacing: TextUnit = 2.2.sp
) {
    Row {
        Text("NE", color = Color.White, fontSize = fontSize, fontWeight = FontWeight.Black, letterSpacing = letterSpacing)
        Text("X", color = BrandViolet, fontSize = fontSize, fontWeight = FontWeight.Black, letterSpacing = letterSpacing)
        Text("ORA", color = Color.White, fontSize = fontSize, fontWeight = FontWeight.Black, letterSpacing = letterSpacing)
    }
}

@Composable
fun PlayerEcosystemWordmark(
    fontSize: TextUnit,
    letterSpacing: TextUnit = 1.1.sp
) {
    Row {
        Text("PLAYER ", color = Color.White.copy(alpha = 0.72f), fontSize = fontSize, fontWeight = FontWeight.Black, letterSpacing = letterSpacing)
        Text("ECO", color = BrandGreen, fontSize = fontSize, fontWeight = FontWeight.Black, letterSpacing = letterSpacing)
        Text("SYSTEM", color = Color.White.copy(alpha = 0.72f), fontSize = fontSize, fontWeight = FontWeight.Black, letterSpacing = letterSpacing)
    }
}
