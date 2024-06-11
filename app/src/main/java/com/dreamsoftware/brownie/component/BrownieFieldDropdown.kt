package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay

data class BrownieDropdownMenuItem(
    val id: String,
    @StringRes val textRes: Int? = null,
    val text: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrownieFieldDropdown(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    value: BrownieDropdownMenuItem? = null,
    menuItems: List<BrownieDropdownMenuItem> = emptyList(),
    @StringRes labelRes: Int,
    @StringRes placeHolderRes: Int,
    errorMessage: String? = null,
    prefix: @Composable (() -> Unit)? = null,
    enableTextFieldSeparator: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @DrawableRes leadingIconRes: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onMenuItemClicked: (BrownieDropdownMenuItem) -> Unit = {},
    onDone: ((FocusManager) -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        },
    ) {
        BrownieDefaultTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            isEnabled = isEnabled,
            isReadOnly = isReadOnly,
            isSingleLine = true,
            errorMessage = errorMessage,
            value = value?.text ?: value?.textRes?.let { stringResource(id = it) },
            leadingIconRes = leadingIconRes,
            leadingIcon = leadingIcon,
            prefix = prefix,
            enableTextFieldSeparator = enableTextFieldSeparator,
            visualTransformation = visualTransformation,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            labelRes = labelRes,
            placeHolderRes = placeHolderRes,
            onDone = onDone
        )
        DropdownMenu(
            modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize(true),
            properties = PopupProperties(focusable = false),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        BrownieText(
                            type = BrownieTextTypeEnum.LABEL_MEDIUM,
                            titleText = item.text ?: item.textRes?.let { stringResource(id = it) }
                        )
                    },
                    onClick = {
                        isExpanded = false
                        onMenuItemClicked(item)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    LaunchedEffect(isExpanded) {
        delay(100)
        keyboardController?.hide()
    }
}