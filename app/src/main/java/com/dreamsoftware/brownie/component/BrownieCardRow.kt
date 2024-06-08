package com.dreamsoftware.brownie.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun BrownieCardRow(
    modifier: Modifier = Modifier,
    contentBrush: Brush? = null,
    contentColor: Color? = null,
    shape: Shape = RoundedCornerShape(27.dp),
    border: BorderStroke = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    content: @Composable RowScope.() -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .then(modifier),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = shape,
        border = border
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .let { modifier ->
                    contentBrush?.let {
                        modifier.then(Modifier.background(it))
                    } ?: contentColor?.let {
                        modifier.then(Modifier.background(it))
                    } ?: modifier
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}