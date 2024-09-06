package com.dreamsoftware.brownie.component

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.brownie.theme.Dimens.HEADER_HEIGHT
import com.dreamsoftware.brownie.theme.Dimens.PADDING_MEDIUM
import com.dreamsoftware.brownie.theme.Dimens.TITLE_FONT_SCALE_END
import com.dreamsoftware.brownie.theme.Dimens.TITLE_FONT_SCALE_START
import com.dreamsoftware.brownie.theme.Dimens.TITLE_PADDING_END
import com.dreamsoftware.brownie.theme.Dimens.TITLE_PADDING_START
import com.dreamsoftware.brownie.theme.Dimens.TOOLBAR_HEIGHT

@Composable
fun BrownieDetailScreen(
    context: Context,
    @DrawableRes defaultImagePlaceholderRes: Int,
    @DrawableRes backIconRes: Int,
    scrollState: ScrollState,
    density: Density,
    isLoading: Boolean = false,
    imageUrl: String? = null,
    title: String? = null,
    infoMessage: String,
    errorMessage: String? = null,
    contentCentered: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val headerHeightPx = with(density) { HEADER_HEIGHT.toPx() }
    val toolbarHeightPx = with(density) { TOOLBAR_HEIGHT.toPx() }
    BrownieScreenContent(
        backgroundRes = null,
        hasTopBar = false,
        infoMessage = infoMessage,
        errorMessage = errorMessage,
        screenContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                //....................
                CommonDetailHeader(
                    context = context,
                    scrollState = scrollState,
                    headerHeightPx = headerHeightPx,
                    defaultImagePlaceholderRes = defaultImagePlaceholderRes,
                    imageUrl = imageUrl
                )
                //....................
                Column(
                    horizontalAlignment = if (contentCentered) {
                        Alignment.CenterHorizontally
                    } else {
                        Alignment.Start
                    },
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                        .verticalScroll(scrollState)
                ) {
                    Spacer(Modifier.height(HEADER_HEIGHT))
                    content()
                }
                //....................
                CommonDetailToolbar(
                    context = context,
                    scrollState = scrollState,
                    headerHeightPx = headerHeightPx,
                    toolbarHeightPx = toolbarHeightPx,
                    imageUrl = imageUrl,
                    isLoading = isLoading,
                    defaultImagePlaceholderRes = defaultImagePlaceholderRes,
                    backIconRes = backIconRes,
                    onBackClicked = onBackClicked
                )
                //....................
                CommonDetailTitle(
                    scrollState = scrollState,
                    headerHeightPx = headerHeightPx,
                    toolbarHeightPx = toolbarHeightPx,
                    title = title
                )
            }
        }
    )
}

@Composable
private fun CommonDetailHeader(
    context: Context,
    scrollState: ScrollState,
    @DrawableRes defaultImagePlaceholderRes: Int,
    imageUrl: String?,
    headerHeightPx: Float
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(HEADER_HEIGHT)
        .graphicsLayer {
            translationY = -scrollState.value.toFloat() / 2f // Parallax effect
            alpha = (-1f / headerHeightPx) * scrollState.value + 1
        }
    ) {
        BrownieAsyncImage(
            modifier = Modifier.fillMaxSize(),
            context = context,
            imageUrl = imageUrl,
            defaultImagePlaceholderRes = defaultImagePlaceholderRes
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4 // Gradient applied to wrap the title only
                    )
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BoxScope.CommonDetailToolbar(
    context: Context,
    @DrawableRes defaultImagePlaceholderRes: Int,
    @DrawableRes backIconRes: Int,
    scrollState: ScrollState,
    headerHeightPx: Float,
    imageUrl: String?,
    toolbarHeightPx: Float,
    isLoading: Boolean,
    onBackClicked: (() -> Unit)? = null
) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            scrollState.value >= toolbarBottom
        }
    }
    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        TopAppBar(
            modifier = Modifier
                .height(TOOLBAR_HEIGHT)
                .background(
                    brush = with(MaterialTheme.colorScheme) {
                        Brush.horizontalGradient(
                            listOf(onPrimary, primary)
                        )
                    }
                ),
            windowInsets = WindowInsets.statusBars
                .only(WindowInsetsSides.Horizontal),
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 15.dp)
                        .fillMaxHeight()
                ) {
                    BrownieAsyncImage(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape),
                        context = context,
                        reverseStyle = true,
                        imageUrl = imageUrl,
                        defaultImagePlaceholderRes = defaultImagePlaceholderRes
                    )
                }
            },
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }

    onBackClicked?.let {
        if (!isLoading && !showToolbar) {
            Image(
                modifier = Modifier
                    .padding(15.dp)
                    .size(35.dp)
                    .align(Alignment.TopStart)
                    .clickable { it() },
                painter = painterResource(backIconRes),
                contentDescription = "Back Icon",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
private fun CommonDetailTitle(
    scrollState: ScrollState,
    title: String?,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    var titleHeightPx by remember { mutableFloatStateOf(0f) }
    var titleWidthPx by remember { mutableFloatStateOf(0f) }
    BrownieText(
        titleText = title.orEmpty(),
        textColor = MaterialTheme.colorScheme.onPrimary,
        type = BrownieTextTypeEnum.HEADLINE_LARGE,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scrollState.value / collapseRange).coerceIn(0f, 1f)
                val scaleXY = lerp(
                    TITLE_FONT_SCALE_START.dp,
                    TITLE_FONT_SCALE_END.dp,
                    collapseFraction
                )
                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f
                val titleYFirstInterpolatedPoint = lerp(
                    HEADER_HEIGHT - titleHeightPx.toDp() - PADDING_MEDIUM,
                    HEADER_HEIGHT / 2,
                    collapseFraction
                )
                val titleXFirstInterpolatedPoint = lerp(
                    TITLE_PADDING_START,
                    (TITLE_PADDING_END - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )
                val titleYSecondInterpolatedPoint = lerp(
                    HEADER_HEIGHT / 2,
                    TOOLBAR_HEIGHT / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )
                val titleXSecondInterpolatedPoint = lerp(
                    (TITLE_PADDING_END - titleExtraStartPadding) * 5 / 4,
                    TITLE_PADDING_END - titleExtraStartPadding,
                    collapseFraction
                )
                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )
                val titleX = lerp(
                    titleXFirstInterpolatedPoint,
                    titleXSecondInterpolatedPoint,
                    collapseFraction
                )
                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned { lc ->
                titleHeightPx = lc.size.height.toFloat()
                titleWidthPx = lc.size.width.toFloat()
            }
    )
}