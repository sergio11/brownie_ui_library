package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BrownieIconButton(
    @DrawableRes iconRes: Int,
    isEnabled: Boolean = true,
    containerSize: Dp = 52.dp,
    iconSize: Dp = 40.dp,
    iconPadding: Dp = 8.dp,
    iconTintColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .clip(CircleShape)
            .shadow(10.dp)
            .background(color = containerColor)
            .clickable {
                if(isEnabled) {
                    onClicked()
                }
            },
    ) {
        Icon(
            modifier = Modifier
                .padding(all = iconPadding)
                .size(iconSize),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTintColor
        )
    }
}