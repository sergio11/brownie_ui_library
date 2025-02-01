package com.dreamsoftware.brownie.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun BrownieFavoriteButton(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    surfaceColor: Color = MaterialTheme.colorScheme.background,
    favoriteColor: Color = Color.Red,
    onCheckedChange: (isChecked: Boolean) -> Unit
) {
    Surface(
        shape = CircleShape,
        modifier = Modifier
            .padding(6.dp)
            .then(modifier),
        color = surfaceColor
    ) {
        IconToggleButton(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        ) {
            Icon(
                tint = favoriteColor,
                modifier = Modifier
                    .padding(8.dp)
                    .graphicsLayer {
                        scaleX = 1.3f
                        scaleY = 1.3f
                    },
                imageVector = if (isChecked) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null
            )
        }
    }
}