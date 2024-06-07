package com.dreamsoftware.brownie.component.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType

/**
 * Composable function to display a Multi-Floating Action Button (Multi-FAB) that can be expanded to reveal sub-items.
 *
 * @param modifier The optional [Modifier] for customizing the layout of the Multi-FAB.
 * @param items The list of [BrownieFabButtonItem] representing the sub-items to be displayed when the Multi-FAB is expanded.
 * @param fabState The [MutableState] representing the current state of the Multi-FAB, whether it is expanded or collapsed.
 * @param fabIcon The [BrownieFabButtonMain] representing the main FAB with an icon and optional rotation.
 * @param fabOption The [BrownieFabButtonSub] representing the customization options for the sub-items.
 * @param onFabItemClicked The callback function to handle click events on the sub-items.
 * @param stateChanged The optional callback function to notify when the state of the Multi-FAB changes (expanded or collapsed).
 */
@Composable
fun BrownieMultiFloatingActionButton(
    items: List<BrownieFabButtonItem>,
    fabState: MutableState<BrownieFabButtonState> = rememberMultiFabState(),
    fabIcon: BrownieFabButtonMain,
    fabOption: BrownieFabButtonSub,
    onFabItemClicked: (fabItem: BrownieFabButtonItem) -> Unit,
    stateChanged: (fabState: BrownieFabButtonState) -> Unit = {}
) {
    // Animation for rotating the main FAB icon based on its state (expanded or collapsed)
    val rotation by animateFloatAsState(
        if (fabState.value == BrownieFabButtonState.Expand) {
            fabIcon.iconRotate ?: 0f
        } else {
            0f
        }, label = "main_fab_rotation"
    )

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End
    ) {
        // AnimatedVisibility to show or hide the sub-items when the Multi-FAB is expanded or collapsed
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            // LazyColumn to display the sub-items in a vertical list
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(items.size) { index ->
                    // Composable to display each individual sub-item
                    BrownieMiniFabItem(
                        item = items[index],
                        fabOption = fabOption,
                        onFabItemClicked = onFabItemClicked
                    )
                }
                item {} // Empty item to provide spacing at the end of the list
            }
        }

        // Main FloatingActionButton representing the Multi-FAB
        FloatingActionButton(
            modifier = Modifier.size(66.dp),
            onClick = {
                fabState.value = fabState.value.toggleValue()
                stateChanged(fabState.value)
            },
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            // Icon for the main FAB with optional rotation based on its state (expanded or collapsed)
            BrownieImageIcon(
                type = BrownieType.ICON,
                size = BrownieImageSize.NORMAL,
                modifier = Modifier
                    .size(32.dp)
                    .rotate(rotation),
                iconRes = fabIcon.iconRes,
                tintColor = fabOption.iconTint
            )
        }
    }
}

/**
 * Composable function to display an individual sub-item of the Multi-Floating Action Button (Multi-FAB).
 *
 * @param item The [BrownieFabButtonItem] representing the sub-item with an icon and label.
 * @param fabOption The [BrownieFabButtonSub] representing the customization options for the sub-items.
 * @param onFabItemClicked The callback function to handle click events on the sub-items.
 */
@Composable
fun BrownieMiniFabItem(
    item: BrownieFabButtonItem,
    fabOption: BrownieFabButtonSub,
    onFabItemClicked: (item: BrownieFabButtonItem) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text label for the sub-item displayed in a rounded-corner background
        BrownieText(
            type = BrownieTextTypeEnum.LABEL_MEDIUM,
            titleRes = item.labelRes,
            titleText = item.label,
            textColor = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color(0xFF000000).copy(alpha = 0.5f))
                .padding(all = 8.dp)
        )

        // FloatingActionButton representing the sub-item
        FloatingActionButton(
            onClick = { onFabItemClicked(item) },
            modifier = Modifier.size(40.dp),
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            // Icon for the sub-item with customized tint
            BrownieImageIcon(
                type = BrownieType.ICON,
                size = BrownieImageSize.SMALL,
                iconRes = item.iconRes,
                tintColor = fabOption.iconTint
            )
        }
    }
}