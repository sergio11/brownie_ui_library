package com.dreamsoftware.brownie.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrownieBottomSheet(
    containerColor: Color = Color.White,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        containerColor = containerColor,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = {
            onDismiss()
        },
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        },
        content = {
            content()
        },
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
    BackHandler(
        onBack = {
            onDismiss()
        }
    )
}