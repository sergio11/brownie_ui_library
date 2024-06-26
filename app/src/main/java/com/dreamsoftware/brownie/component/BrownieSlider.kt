package com.dreamsoftware.brownie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BrownieSlider(
    modifier: Modifier = Modifier,
    title: String,
    value: Float,
    steps: Int,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier
        .background(Color.White, RoundedCornerShape(27.dp))
        .then(modifier)
    ) {
        BrownieText(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxWidth(),
            type = BrownieTextTypeEnum.TITLE_MEDIUM,
            titleText = title,
            textColor = MaterialTheme.colorScheme.primary
        )
        Slider(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = value,
            valueRange = valueRange,
            steps = steps,
            onValueChange = onValueChange
        )
    }
}