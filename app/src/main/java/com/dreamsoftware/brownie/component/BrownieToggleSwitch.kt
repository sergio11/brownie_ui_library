package com.dreamsoftware.brownie.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BrownieToggleSwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    @StringRes labelRes: Int? = null,
    labelTitle: String? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    checkedThumbColor: Color? = null,
    uncheckedThumbColor: Color? = null,
    checkedTrackColor: Color? = null,
    uncheckedTrackColor: Color? = null
) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrownieText(
            modifier = Modifier.weight(1f),
            type = BrownieTextTypeEnum.LABEL_SMALL,
            titleRes = labelRes,
            titleText = labelTitle,
            maxLines = 2
        )
        Switch(
            checked = isChecked,
            onCheckedChange = { onCheckedChange?.invoke(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = checkedThumbColor ?: colorScheme.primary,
                uncheckedThumbColor = uncheckedThumbColor ?: colorScheme.onSurfaceVariant,
                checkedTrackColor = checkedTrackColor ?: colorScheme.primaryContainer,
                uncheckedTrackColor = uncheckedTrackColor ?: colorScheme.surfaceVariant
            )
        )
    }
}