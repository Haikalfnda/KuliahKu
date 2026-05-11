package com.haikal0045.kuliahku.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun KuliahKuTheme(
    themeColor: Int = 0,
    content: @Composable () -> Unit
) {
    val primaryColor = when (themeColor) {
        1 -> Color(0xFF1565C0)
        2 -> Color(0xFF2E7D32)
        3 -> Color(0xFFC62828)
        else -> Color(0xFF6750A4)
    }

    val primaryContainerColor = when (themeColor) {
        1 -> Color(0xFFD3E3FD)
        2 -> Color(0xFFC8E6C9)
        3 -> Color(0xFFFFCDD2)
        else -> Color(0xFFEADDFF)
    }

    val colorScheme = lightColorScheme(
        primary = primaryColor,
        onPrimary = Color.White,
        primaryContainer = primaryContainerColor,
        onPrimaryContainer = primaryColor,
        secondary = primaryColor,
        background = Color(0xFFFFFBFE),
        surface = Color(0xFFFFFBFE)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}