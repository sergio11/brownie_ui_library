package com.dreamsoftware.brownie.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun BrownieCardColumn(
    modifier: Modifier = Modifier,
    elevation: CardElevation = CardDefaults.outlinedCardElevation(),
    colors: CardColors = CardDefaults.outlinedCardColors(),
    shape: Shape = RoundedCornerShape(27.dp),
    border: BorderStroke = BorderStroke(5.dp, MaterialTheme.colorScheme.primary),
    content: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .padding(20.dp)
            .then(modifier),
        elevation = elevation,
        colors = colors,
        shape = shape,
        border = border
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 5.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}