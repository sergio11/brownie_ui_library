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
    onItemClicked: (BottomNavBarItem) -> Unit = {}
) {
    with(MaterialTheme.colorScheme) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = primary,
                    spotColor = primary,
                    clip = true
                )
        ) {
            NavigationBar(
                containerColor = surface,
                contentColor = onSurface
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
                                    onPrimary
                                } else {
                                    primary
                                }
                            )
                        },
                        label = {
                            destination.titleRes?.let {
                                BrownieText(type = BrownieTextTypeEnum.LABEL_SMALL, titleRes = it)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = primary,
                            indicatorColor = primary
                        )
                    )
                }
            }
        }
    }
}