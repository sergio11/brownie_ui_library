package com.dreamsoftware.brownie.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> BrownieChipFilterBar(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    itemLabelProvider: @Composable (T) -> String,
    chipBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    chipSelectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    chipLabelColor: Color = MaterialTheme.colorScheme.onSurface,
    chipSelectedLabelColor: Color = MaterialTheme.colorScheme.onPrimary,
    chipIconColor: Color = MaterialTheme.colorScheme.onSurface,
    chipSelectedIconColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    LazyRow(
        modifier = modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items) { item ->
            FilterChipItem(
                item = item,
                isSelected = item == selectedItem,
                onClick = { onItemSelected(item) },
                itemLabelProvider = itemLabelProvider,
                chipBackgroundColor = chipBackgroundColor,
                chipSelectedBackgroundColor = chipSelectedBackgroundColor,
                chipLabelColor = chipLabelColor,
                chipSelectedLabelColor = chipSelectedLabelColor,
                chipIconColor = chipIconColor,
                chipSelectedIconColor = chipSelectedIconColor
            )
        }
    }
}

@Composable
private fun <T> FilterChipItem(
    item: T,
    isSelected: Boolean,
    onClick: () -> Unit,
    itemLabelProvider: @Composable (T) -> String,
    chipBackgroundColor: Color,
    chipSelectedBackgroundColor: Color,
    chipLabelColor: Color,
    chipSelectedLabelColor: Color,
    chipIconColor: Color,
    chipSelectedIconColor: Color
) {
    ElevatedFilterChip(
        onClick = onClick,
        selected = isSelected,
        colors = FilterChipDefaults.elevatedFilterChipColors(
            containerColor = if (isSelected) chipSelectedBackgroundColor else chipBackgroundColor,
            labelColor = if (isSelected) chipSelectedLabelColor else chipLabelColor,
            iconColor = if (isSelected) chipSelectedIconColor else chipIconColor
        ),
        leadingIcon = {
            AnimatedVisibility(isSelected) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Selected filter"
                )
            }
        },
        label = {
            BrownieText(
                type = BrownieTextTypeEnum.LABEL_MEDIUM,
                titleText = itemLabelProvider(item)
            )
        }
    )
}