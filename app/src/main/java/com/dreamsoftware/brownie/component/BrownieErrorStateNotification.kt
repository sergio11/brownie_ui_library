package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.R

@Composable
fun BrownieErrorStateNotification(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    @DrawableRes imageRes: Int,
    title: String,
    isRetryButtonVisible: Boolean = false,
    onRetryCalled: () -> Unit = {}
) {
    if (isVisible) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .then(modifier)) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        Color.White.copy(alpha = 0.6f),
                        RoundedCornerShape(percent = 10)
                    )
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(10.dp),
                    painter = painterResource(id = imageRes),
                    contentDescription = "Content Description",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                BrownieText(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 8.dp),
                    type = BrownieTextTypeEnum.TITLE_LARGE,
                    titleText = title,
                    textColor = MaterialTheme.colorScheme.inversePrimary,
                    textAlign = TextAlign.Center
                )
                if (isRetryButtonVisible) {
                    BrownieButton(
                        text = R.string.retry_button_text,
                        containerColor = MaterialTheme.colorScheme.primary,
                        onClick = onRetryCalled
                    )
                }
            }
        }
    }
}