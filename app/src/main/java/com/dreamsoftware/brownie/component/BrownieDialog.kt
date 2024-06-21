package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun BrownieDialog(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes mainLogoRes: Int,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    @StringRes cancelRes: Int? = null,
    @StringRes acceptRes: Int? = null,
    onCancelClicked: () -> Unit = {},
    onAcceptClicked: () -> Unit = {},
    isAcceptEnabled: Boolean = true,
    customContent: @Composable ColumnScope.() -> Unit = {}
) {
    if(isVisible) {
        Dialog(onDismissRequest = onCancelClicked) {
            BrownieDialogUI(
                modifier,
                mainLogoRes,
                titleRes,
                descriptionRes,
                cancelRes,
                acceptRes,
                onCancelClicked,
                onAcceptClicked,
                isAcceptEnabled,
                customContent
            )
        }
    }
}

@Composable
internal fun BrownieDialogUI(
    modifier: Modifier = Modifier,
    @DrawableRes mainLogoRes: Int,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    @StringRes cancelRes: Int? = null,
    @StringRes successRes: Int? = null,
    onCancelClicked: () -> Unit,
    onAcceptClicked: () -> Unit,
    isAcceptEnabled: Boolean = true,
    customContent: @Composable ColumnScope.() -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = modifier.background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = mainLogoRes),
                contentDescription = null, // decorative
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(200.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                BrownieText(
                    type = BrownieTextTypeEnum.LABEL_LARGE,
                    titleRes = titleRes,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
                BrownieText(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    type = BrownieTextTypeEnum.BODY_MEDIUM,
                    titleRes = descriptionRes,
                    textAlign = TextAlign.Center
                )
            }
            customContent()
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                cancelRes?.let {
                    TextButton(onClick = onCancelClicked) {
                        BrownieText(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            type = BrownieTextTypeEnum.LABEL_SMALL,
                            titleText = stringResource(id = it),
                            textColor = Color.Gray
                        )
                    }
                }
                successRes?.let {
                    TextButton(enabled = isAcceptEnabled, onClick = onAcceptClicked) {
                        BrownieText(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            type = BrownieTextTypeEnum.LABEL_SMALL,
                            titleText = stringResource(id = it),
                            textColor = Color.Black
                        )
                    }
                }
            }
        }
    }
}