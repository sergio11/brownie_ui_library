package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BrownieColumnPlaceHolder(
    @StringRes titleRes: Int? = null,
    titleText: String? = null,
    @DrawableRes iconRes: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BrownieImageIcon(
            size = BrownieImageSize.LARGE,
            iconRes = iconRes
        )
        Spacer(modifier = Modifier.height(24.dp))
        BrownieText(
            type = BrownieTextTypeEnum.LABEL_LARGE,
            titleText = titleRes?.let { stringResource(id = it) } ?: titleText,
            textAlign = TextAlign.Center
        )
    }
}