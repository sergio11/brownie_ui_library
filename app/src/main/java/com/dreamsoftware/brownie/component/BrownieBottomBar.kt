package com.dreamsoftware.brownie.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.ui.graphics.Color

data class BottomNavBarItem(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val titleRes: Int? = null,
    val alwaysShowLabels: Boolean = true
)

@SuppressLint("RestrictedApi")
@Composable
fun BrownieBottomBar(
    items: List<BottomNavBarItem>,
    currentItemRouteSelected: String?,
    enableWindowInsets: Boolean = true,
    ambientColor: Color = MaterialTheme.colorScheme.primary,
    spotColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconColorSelected: Color = MaterialTheme.colorScheme.primaryContainer,
    onItemClicked: (BottomNavBarItem) -> Unit = {}
) {
    with(MaterialTheme.colorScheme) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = ambientColor,
                    spotColor = spotColor,
                    clip = true
                )
        ) {
            NavigationBar(
                windowInsets = if(enableWindowInsets) {
                    NavigationBarDefaults.windowInsets
                } else {
                    WindowInsets(0)
                },
                containerColor = containerColor,
                contentColor = contentColor
            ) {
                items.forEach { destination ->
                    NavigationBarItem(
                        selected = currentItemRouteSelected == destination.route,
                        onClick = { onItemClicked(destination) },
                        icon = {
                            BrownieImageIcon(
                                type = BrownieType.ICON,
                                iconRes = destination.icon,
                                tintColor = if (currentItemRouteSelected == destination.route) {
                                    iconColorSelected
                                } else {
                                    onPrimary
                                }
                            )
                        },
                        label = {
                            destination.titleRes?.let {
                                BrownieText(
                                    type = BrownieTextTypeEnum.LABEL_SMALL,
                                    titleRes = it,
                                    textColor = onPrimary
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = onPrimary,
                            indicatorColor = onPrimary
                        )
                    )
                }
            }
        }
    }
}