package com.dreamsoftware.brownie.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BrownieSelectionRow(
    @StringRes titleRes: Int,
    checked: Boolean,
    checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
    uncheckedTrackColor: Color = MaterialTheme.colorScheme.primaryContainer,
    uncheckedBorderColor: Color = MaterialTheme.colorScheme.primaryContainer,
    uncheckedThumbColor: Color = Color.White,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrownieText(
            type = BrownieTextTypeEnum.LABEL_LARGE,
            titleRes = titleRes
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            modifier = Modifier.size(42.dp),
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = checkedTrackColor,
                uncheckedTrackColor = uncheckedTrackColor,
                uncheckedBorderColor = uncheckedBorderColor,
                uncheckedThumbColor = uncheckedThumbColor
            )
        )
    }
}
