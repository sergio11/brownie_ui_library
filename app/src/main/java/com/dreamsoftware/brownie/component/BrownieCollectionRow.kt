package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.R

@Composable
fun <T: Any>BrownieCollectionRow(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    reverseStyle: Boolean = false,
    items: List<T>,
    onBuildItem: @Composable LazyItemScope.(modifier: Modifier, item: T) -> Unit,
    onShowAllItems: (() -> Unit)? = null,
    @DrawableRes onShowAllItemsIconRes: Int = R.drawable.arrow_right_icon,
    onItemSelected: (item: T) -> Unit = {}
) {
    if (items.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BrownieText(
                    modifier = Modifier.padding(8.dp),
                    type = BrownieTextTypeEnum.TITLE_LARGE,
                    titleText = stringResource(id = titleRes),
                    textColor = if (reverseStyle) {
                        Color.Black
                    } else {
                        Color.White
                    },
                    maxLines = 2
                )
                onShowAllItems?.let {
                    Image(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(35.dp)
                            .clickable { it() },
                        painter = painterResource(onShowAllItemsIconRes),
                        contentDescription = "onShowAllItems",
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(if (reverseStyle) {
                            Color.Black
                        } else {
                            Color.White
                        })
                    )
                }
            }
            LazyRow(
                modifier = Modifier.padding(vertical = 10.dp),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items.size) { idx ->
                    with(items[idx]) {
                        onBuildItem(
                            Modifier.clickable {
                                onItemSelected(this)
                            },
                            this
                        )
                    }
                }
            }
        }
    }
}