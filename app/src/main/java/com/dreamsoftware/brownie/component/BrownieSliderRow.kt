package com.dreamsoftware.brownie.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrownieSliderRow(
    @StringRes titleRes: Int,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    activeTrackColor: Color = MaterialTheme.colorScheme.primaryContainer,
    inactiveTrackColor: Color = MaterialTheme.colorScheme.primaryContainer,
    thumbTextColor: Color = MaterialTheme.colorScheme.primary, // Changed to black
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrownieText(
            type = BrownieTextTypeEnum.LABEL_LARGE,
            titleRes = titleRes
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                activeTickColor = activeTrackColor,
                activeTrackColor = activeTrackColor,
                inactiveTrackColor = inactiveTrackColor,
                thumbColor = Color.Transparent // Set thumb color to transparent
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .padding(6.dp), // Add padding to create the border
                    contentAlignment = Alignment.Center,
                    content = {
                        Box(
                            modifier = Modifier
                                .size(32.dp) // Adjust the size of the inner white circle
                                .background(color = Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center,
                            content = {
                                BrownieText(
                                    type = BrownieTextTypeEnum.LABEL_MEDIUM,
                                    titleText = value.toInt().toString(),
                                    textColor = thumbTextColor
                                )
                            }
                        )
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            interactionSource = interactionSource,
            onValueChangeFinished = null
        )
    }
}