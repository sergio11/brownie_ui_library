package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.theme.montserratFontFamily

val BrownieDefaultTextFieldModifier = Modifier
    .padding(vertical = 15.dp)
    .width(300.dp)

@Composable
fun BrownieDefaultTextField(
    modifier: Modifier = BrownieDefaultTextFieldModifier,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    value: String? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    @StringRes labelRes: Int,
    @StringRes placeHolderRes: Int,
    @StringRes suffixRes: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    prefix: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChanged: (newValue: String) -> Unit = {},
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingIconRes: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: (() -> String)? = null,
    onDone: ((FocusManager) -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = value.orEmpty(),
        enabled = isEnabled,
        readOnly = isReadOnly,
        prefix = prefix,
        visualTransformation = visualTransformation,
        supportingText = {
            supportingText?.invoke()?.let { text ->
                Text(text = text, fontFamily = montserratFontFamily)
            }
        },
        label = { Text(stringResource(id = labelRes), fontFamily = montserratFontFamily) },
        placeholder = {
            Text(
                stringResource(id = placeHolderRes),
                fontFamily = montserratFontFamily
            )
        },
        suffix = {
            suffixRes?.let {
                Text(
                    text = stringResource(id = it),
                    fontFamily = montserratFontFamily
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone?.invoke(focusManager) ?: run {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            }
        ),
        singleLine = isSingleLine,
        leadingIcon = {
            leadingIconRes?.let {
                BrownieImageIcon(
                    modifier = Modifier.size(22.dp),
                    iconRes = it,
                    tintColor = MaterialTheme.colorScheme.primary
                )
            } ?: leadingIcon
        },
        trailingIcon = {
            trailingIconRes?.let {
                BrownieImageIcon(
                    modifier = Modifier.size(22.dp),
                    iconRes = it,
                    tintColor = MaterialTheme.colorScheme.primary
                )
            } ?: trailingIcon
        },
        colors = colors,
        shape = RoundedCornerShape(27.dp),
        maxLines = if (isSingleLine) 1 else maxLines,
        onValueChange = onValueChanged
    )
}