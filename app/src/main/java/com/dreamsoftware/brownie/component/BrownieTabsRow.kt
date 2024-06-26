package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.utils.tabSelectedIndex

data class BrownieTabUi<out T>(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    var isSelected: Boolean = false,
    val type: T
)

@Composable
fun <T>BrownieTabsRow(
    tabs: List<BrownieTabUi<T>>,
    onNewTabSelected: (type: T) -> Unit,
) {
    with(tabs) {
        if (isNotEmpty()) {
            TabRow(
                selectedTabIndex = tabSelectedIndex(),
                containerColor = Color.White.copy(alpha = 0.9f)) {
                forEach { tab ->
                    Tab(
                        selected = tab.isSelected,
                        onClick = { onNewTabSelected(tab.type) },
                        icon = {
                            Image(
                                painter = painterResource(tab.iconRes),
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}