package com.dreamsoftware.brownie.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private const val SLIDE_DOWN_IN_DURATION = 250
private const val SLIDE_DOWN_OUT_DURATION = 250

@Composable
fun BrownieSlideDownAnimatedVisibility(
    modifier: Modifier = Modifier,
    inDurationInMillis: Int = SLIDE_DOWN_IN_DURATION,
    outDurationInMillis: Int = SLIDE_DOWN_OUT_DURATION,
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = inDurationInMillis,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = outDurationInMillis,
                easing = FastOutLinearInEasing
            )
        ),
        content = content
    )
}