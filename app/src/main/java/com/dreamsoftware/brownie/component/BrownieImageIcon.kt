package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BrownieImageIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int
) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = "",
        modifier = Modifier
            .size(32.dp)
            .then(modifier),
    )
}