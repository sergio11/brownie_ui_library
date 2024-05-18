package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.utils.EMPTY

enum class BrownieImageSize {
    SMALL, NORMAL, LARGE
}

enum class BrownieType {
    IMAGE, ICON
}

private val IMAGE_SMALL_SIZE = 16.dp
private val IMAGE_NORMAL_SIZE = 32.dp
private val IMAGE_LARGE_SIZE = 64.dp

@Composable
fun BrownieImageIcon(
    modifier: Modifier = Modifier,
    type: BrownieType = BrownieType.IMAGE,
    size: BrownieImageSize = BrownieImageSize.NORMAL,
    @DrawableRes iconRes: Int,
    tintColor: Color? = null
) {
    val sizeResult = when(size) {
        BrownieImageSize.SMALL -> IMAGE_SMALL_SIZE
        BrownieImageSize.NORMAL -> IMAGE_NORMAL_SIZE
        BrownieImageSize.LARGE -> IMAGE_LARGE_SIZE
    }
    val imageModifier = Modifier
        .size(sizeResult)
        .then(modifier)
    when(type) {
        BrownieType.IMAGE -> Image(
            painter = painterResource(iconRes),
            contentDescription = String.EMPTY,
            modifier = imageModifier
        )
        BrownieType.ICON -> Icon(
            modifier = imageModifier,
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = String.EMPTY,
            tint = tintColor ?: MaterialTheme.colorScheme.primary
        )
    }
}