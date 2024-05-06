package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BrownieLoadingDialog(
    isShowingDialog: Boolean,
    @DrawableRes mainLogoRes: Int,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int
) {
    BrownieDialog(
        isVisible = isShowingDialog,
        mainLogoRes = mainLogoRes,
        titleRes = titleRes,
        descriptionRes = descriptionRes
    ) {
        DialogContent()
    }
}

@Composable
private fun DialogContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(
                    Alignment.Center
                ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}