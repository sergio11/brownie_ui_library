package com.dreamsoftware.brownie.component

import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest

@Composable
fun BrownieAsyncImage(
    modifier: Modifier,
    context: Context,
    @DrawableRes defaultImagePlaceholderRes: Int,
    reverseStyle: Boolean = false,
    imageUrl: String? = null,
    imageUri: Uri? = null,
    colorFilter: ColorFilter? = null
) {
    (imageUrl ?: imageUri)?.let {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(it)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            colorFilter = colorFilter
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                DefaultImagePlaceholder(modifier, reverseStyle, defaultImagePlaceholderRes)
            } else {
                SubcomposeAsyncImageContent(modifier)
            }
        }
    } ?: run {
        DefaultImagePlaceholder(modifier, reverseStyle, defaultImagePlaceholderRes)
    }
}

@Composable
private fun DefaultImagePlaceholder(
    modifier: Modifier,
    reverseStyle: Boolean = false,
    @DrawableRes defaultImagePlaceholderRes: Int,
) {
    Image(
        painter = painterResource(defaultImagePlaceholderRes),
        colorFilter = with(MaterialTheme.colorScheme) {
            ColorFilter.tint(if(reverseStyle) {
                background
            } else {
                primary
            })
        },
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = with(MaterialTheme.colorScheme) {
            modifier.background(
                if (reverseStyle) {
                    primary
                } else {
                    background
                }
            )
        }
    )
}