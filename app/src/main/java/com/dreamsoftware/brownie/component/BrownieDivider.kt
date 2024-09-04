package com.dreamsoftware.brownie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DEFAULT_DIVIDER_HEIGHT = 1.dp

@Composable
fun BrownieDivider(
    height: Dp = DEFAULT_DIVIDER_HEIGHT,
    color: Color = Color.LightGray
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color)
    )
}