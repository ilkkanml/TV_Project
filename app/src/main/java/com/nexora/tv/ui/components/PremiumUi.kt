package com.nexora.tv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexora.tv.ui.theme.NexoraColors

@Composable
fun PremiumBackground(
    modifier: Modifier = Modifier,
    accent: Color = NexoraColors.Cyan,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF071625),
                        NexoraColors.Black,
                        Color(0xFF02040A)
                    )
                )
            ),
        contentAlignment = contentAlignment
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            accent.copy(alpha = 0.20f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.42f)
                        )
                    )
                )
        )
        content()
    }
}

@Composable
fun PremiumPanel(
    modifier: Modifier = Modifier,
    accent: Color = NexoraColors.Cyan,
    cornerRadius: Dp = 28.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Column(
        modifier = modifier
            .background(NexoraColors.Surface.copy(alpha = 0.88f), shape)
            .border(1.dp, accent.copy(alpha = 0.42f), shape)
            .padding(24.dp),
        content = content
    )
}

@Composable
fun PremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accent: Color = NexoraColors.Cyan,
    primary: Boolean = true,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) accent else NexoraColors.SurfaceSoft,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF26303D),
            disabledContentColor = NexoraColors.TextSecondary
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
