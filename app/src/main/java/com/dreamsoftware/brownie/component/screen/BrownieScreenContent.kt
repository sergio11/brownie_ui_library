package com.dreamsoftware.brownie.component.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieScreenBackgroundImage
import com.dreamsoftware.brownie.component.BrownieTopAppBar
import com.dreamsoftware.brownie.component.BrownieTopBarAction

@Composable
fun BrownieScreenContent(
    @StringRes titleRes: Int? = null,
    @DrawableRes backgroundRes: Int? = null,
    titleText: String? = null,
    centerTitle: Boolean = false,
    menuActions: List<BrownieTopBarAction> = emptyList(),
    navigationAction: BrownieTopBarAction? = null,
    screenContainerColor: Color = MaterialTheme.colorScheme.background,
    hasTopBar: Boolean = true,
    enableVerticalScroll: Boolean = false,
    errorMessage: String? = null,
    onErrorMessageCleared: () -> Unit = {},
    infoMessage: String? = null,
    onInfoMessageCleared: () -> Unit = {},
    enableContentWindowInsets: Boolean = false,
    backgroundLayerColor: Color = MaterialTheme.colorScheme.primary.copy(0.4f),
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    drawBottomBarOverContent: Boolean = false,
    onBuildFloatingActionButton: @Composable (() -> Unit)? = null,
    onBuildBottomBar: @Composable (() -> Unit)? = null,
    onBuildCustomTopBar: @Composable (() -> Unit)? = null,
    onBuildBackgroundContent: @Composable (BoxScope.() -> Unit)? = null,
    screenContent: @Composable ColumnScope.() -> Unit = {}
) {
    val snackBarErrorHostState = remember { SnackbarHostState() }
    val snackBarInfoHostState = remember { SnackbarHostState() }
    if (!errorMessage.isNullOrBlank()) {
        LaunchedEffect(snackBarErrorHostState) {
            snackBarErrorHostState.showSnackbar(message = errorMessage)
            onErrorMessageCleared()
        }
    }
    if (!infoMessage.isNullOrBlank()) {
        LaunchedEffect(snackBarInfoHostState) {
            snackBarInfoHostState.showSnackbar(message = infoMessage)
            onInfoMessageCleared()
        }
    }
    Scaffold(
        bottomBar = {
            if(!drawBottomBarOverContent) {
                onBuildBottomBar?.invoke()
            }
        },
        floatingActionButton = {
            onBuildFloatingActionButton?.invoke()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackbarHost = {
            SnackbarHost(snackBarErrorHostState) { data ->
                with(MaterialTheme.colorScheme) {
                    Snackbar(
                        shape = RoundedCornerShape(10.dp),
                        containerColor = errorContainer,
                        actionColor = onError,
                        contentColor = onError,
                        snackbarData = data
                    )
                }
            }
            SnackbarHost(snackBarInfoHostState) { data ->
                with(MaterialTheme.colorScheme) {
                    Snackbar(
                        shape = RoundedCornerShape(10.dp),
                        containerColor = primaryContainer,
                        actionColor = onPrimaryContainer,
                        contentColor = onPrimaryContainer,
                        snackbarData = data
                    )
                }
            }
        },
        topBar = {
            if (hasTopBar) {
                onBuildCustomTopBar?.invoke() ?: BrownieTopAppBar(
                    titleRes = titleRes,
                    titleText = titleText,
                    navigationAction = navigationAction,
                    centerTitle = centerTitle,
                    menuActions = menuActions
                )
            }
        },
        contentWindowInsets = if(enableContentWindowInsets) {
            ScaffoldDefaults.contentWindowInsets
        } else {
            WindowInsets(0)
        },
        containerColor = screenContainerColor
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .background(screenContainerColor)) {
            if(backgroundRes != null || onBuildBackgroundContent != null) {
                if(backgroundRes != null) {
                    BrownieScreenBackgroundImage(imageRes = backgroundRes)
                } else {
                    onBuildBackgroundContent?.invoke(this)
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundLayerColor))
            }
            Column(
                modifier = if (enableVerticalScroll) {
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                } else {
                    Modifier.fillMaxWidth()
                }
            ) {
                screenContent()
            }
            if(drawBottomBarOverContent) {
                onBuildBottomBar?.let { bottomBar ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .align(Alignment.BottomCenter)
                    ) {
                        bottomBar.invoke()
                    }
                }
            }
        }
    }
}