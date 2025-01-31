package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.utils.EMPTY

data class BrownieDropdownMenuButtonItem(
    val id: String = String.EMPTY,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
)

@Composable
fun BrownieDropdownMenuButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    isEnabled: Boolean = true,
    containerSize: Dp = 52.dp,
    iconSize: Dp = 40.dp,
    iconPadding: Dp = 8.dp,
    iconTintColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    dropdownMenuContainerColor: Color = MaterialTheme.colorScheme.background,
    dropdownMenuItemIconColor: Color = MaterialTheme.colorScheme.primary,
    dropdownMenuItems: List<BrownieDropdownMenuButtonItem> = emptyList(),
    onMenuItemClicked: (BrownieDropdownMenuButtonItem) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        BrownieIconButton(
            iconRes = iconRes,
            isEnabled = isEnabled,
            containerSize = containerSize,
            iconSize = iconSize,
            iconPadding = iconPadding,
            iconTintColor = iconTintColor,
            containerColor = containerColor
        ) {
            expanded = true
        }
        if(isEnabled && dropdownMenuItems.isNotEmpty()) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(dropdownMenuContainerColor)
            ) {
                dropdownMenuItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            BrownieText(
                                type = BrownieTextTypeEnum.LABEL_MEDIUM,
                                titleRes = item.titleRes
                            )
                        },
                        onClick = {
                            expanded = false
                            onMenuItemClicked(item)
                        },
                        trailingIcon = {
                            BrownieImageIcon(
                                type = BrownieType.ICON,
                                size = BrownieImageSize.SMALL,
                                iconRes = item.iconRes,
                                tintColor = dropdownMenuItemIconColor
                            )
                        }
                    )
                }
            }
        }
    }
}