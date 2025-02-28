package com.dreamsoftware.brownie.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun BrownieEditButton(
    modifier: Modifier = Modifier,
    surfaceColor: Color = MaterialTheme.colorScheme.background,
    editColor: Color = Color.Blue,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        modifier = Modifier
            .padding(6.dp)
            .then(modifier),
        color = surfaceColor
    ) {
        IconButton(onClick = onClick) {
            Icon(
                tint = editColor,
                modifier = Modifier
                    .padding(8.dp)
                    .graphicsLayer {
                        scaleX = 1.3f
                        scaleY = 1.3f
                    },
                imageVector = Icons.Filled.Edit,
                contentDescription = null
            )
        }
    }
}