package com.dreamsoftware.brownie.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BrownieRatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    maxStars: Int = 5,
    starSize: Dp = 40.dp
) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..maxStars) {
            val imageVector = when {
                i <= rating -> Icons.Filled.Star
                i - 0.5f <= rating -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Outlined.Star
            }
            Icon(
                imageVector = imageVector,
                contentDescription = "Star rating",
                modifier = Modifier
                    .size(starSize)
                    .clickable { onRatingChanged(i.toFloat()) },
                tint = if (i <= rating) Color.Yellow else Color.Gray
            )
        }
    }
}