package com.dreamsoftware.brownie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BrownieSheetSurface(
    modifier: Modifier = Modifier,
    surfaceColor: Color = Color.White,
    enableVerticalScroll: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onSurfaceContent: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = surfaceColor
    ) {
        Column(
            modifier = if (enableVerticalScroll) {
                Modifier.verticalScroll(rememberScrollState())
            } else {
                Modifier
            }
                .fillMaxSize()
                .background(color = surfaceColor),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            onSurfaceContent()
        }
    }
}