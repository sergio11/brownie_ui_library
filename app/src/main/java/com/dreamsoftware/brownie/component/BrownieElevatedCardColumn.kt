package com.dreamsoftware.brownie.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DEFAULT_ELEVATION = 4.dp

@Composable
fun BrownieElevatedCardColumn(
    modifier: Modifier = Modifier,
    elevation: Dp = DEFAULT_ELEVATION,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    shape: Shape = RoundedCornerShape(27.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .padding(top = 16.dp)
            .then(modifier),
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
            colors = colors,
            shape = shape,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 24.dp),
                content = content
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}