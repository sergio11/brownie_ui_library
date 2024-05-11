package com.dreamsoftware.brownie.component.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    onBuildFloatingActionButton: @Composable (() -> Unit)? = null,
    onBuildBottomBar: @Composable (() -> Unit)? = null,
    onBuildCustomTopBar: @Composable (() -> Unit)? = null,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    screenContent: @Composable ColumnScope.() -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }
    if (!errorMessage.isNullOrBlank()) {
        LaunchedEffect(snackBarHostState) {
            snackBarHostState.showSnackbar(
                message = errorMessage
            )
        }
    }
    Scaffold(
        bottomBar = {
            onBuildBottomBar?.invoke()
        },
        floatingActionButton = {
            onBuildFloatingActionButton?.invoke()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackbarHost = {
            SnackbarHost(snackBarHostState) { data ->
                with(MaterialTheme.colorScheme) {
                    Snackbar(
                        shape = RoundedCornerShape(10.dp),
                        containerColor = background,
                        actionColor = primary,
                        contentColor = onBackground,
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
        containerColor = screenContainerColor
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            backgroundRes?.let {
                BrownieScreenBackgroundImage(imageRes = it)
            }
            Column(
                modifier = if (enableVerticalScroll) {
                    Modifier.verticalScroll(rememberScrollState())
                } else {
                    Modifier.fillMaxWidth()
                }.background(MaterialTheme.colorScheme.background)
            ) {
                screenContent()
            }
            backgroundContent()
        }
    }
}