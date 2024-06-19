package com.dreamsoftware.brownie.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BrownieColumnProgressIndicator(
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    loadingBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    loadingIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    textIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    @StringRes textIndicatorRes: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(loadingBackgroundColor, shape = RoundedCornerShape(12.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = loadingIndicatorColor,
                    modifier = Modifier
                        .size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                BrownieText(
                    type = BrownieTextTypeEnum.LABEL_LARGE,
                    titleRes = textIndicatorRes,
                    textBold = true,
                    textColor = textIndicatorColor
                )
            }
        }
    }
}