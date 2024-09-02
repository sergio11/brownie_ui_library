package com.dreamsoftware.brownie.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun BrownieCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    shape: Shape = RoundedCornerShape(27.dp),
    border: BorderStroke = BorderStroke(5.dp, MaterialTheme.colorScheme.primary),
    content: @Composable BoxScope.() -> Unit
) {
    OutlinedCard(
        modifier = Modifier.then(modifier),
        colors = colors,
        shape = shape,
        border = border
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}