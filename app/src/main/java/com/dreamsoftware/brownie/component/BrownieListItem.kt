package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.R
import com.dreamsoftware.brownie.utils.EMPTY

@Composable
fun BrownieListItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int? = null,
    title: String? = null,
    @DrawableRes arrowIconRes: Int? = null,
    showArrow: Boolean = true,
    showDivider: Boolean = true,
    onItemPressed: () -> Unit = {}
) {
    with(MaterialTheme.colorScheme) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .clickable { onItemPressed() }
            ) {
                BrownieImageIcon(iconRes = iconRes)
                Spacer(modifier = Modifier.size(16.dp))
                BrownieText(
                    modifier = Modifier.weight(1f),
                    type = BrownieTextTypeEnum.TITLE_MEDIUM,
                    titleText = titleRes?.let { stringResource(id = it) } ?: title ?: String.EMPTY
                )
                if(showArrow) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = arrowIconRes ?: R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp),
                        tint = onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }
            if (showDivider) {
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .width(200.dp)
                        .background(color = onSurfaceVariant.copy(alpha = 0.2f))
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}