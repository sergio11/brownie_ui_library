package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class BrownieTopBarAction(
    @DrawableRes val iconRes: Int,
    val onActionClicked: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrownieTopAppBar(
    @StringRes titleRes: Int? = null,
    titleText: String? = null,
    centerTitle: Boolean = false,
    navigationAction: BrownieTopBarAction? = null,
    menuActions: List<BrownieTopBarAction> = emptyList()
) {
    TopAppBar(
        title = {
            BrownieText(
                type = BrownieTextTypeEnum.HEADLINE_SMALL,
                modifier = if (centerTitle) {
                    Modifier
                        .fillMaxWidth()
                        .offset(
                            x = if (navigationAction != null) {
                                (-25).dp
                            } else {
                                0.dp
                            }
                        )
                } else {
                    Modifier
                },
                titleRes = titleRes,
                titleText = titleText,
                textAlign = TextAlign.Center,
                textColor = Color.White
            )
        },
        navigationIcon = {
            navigationAction?.let {
                IconButton(onClick = it.onActionClicked) {
                    Image(
                        painter = painterResource(it.iconRes),
                        contentDescription = "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(42.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        },
        actions = {
            menuActions.map {
                IconButton(onClick = it.onActionClicked) {
                    Image(
                        painter = painterResource(it.iconRes),
                        contentDescription = "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(25.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}