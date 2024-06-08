package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.core.BrownieTextFieldSeparator
import com.dreamsoftware.brownie.theme.montserratFontFamily

val BrownieTextFieldPasswordModifier = Modifier
    .padding(vertical = 20.dp)
    .width(300.dp)

@Composable
fun BrownieTextFieldPassword(
    modifier: Modifier = BrownieTextFieldPasswordModifier,
    value: String? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    supportingText: (() -> String)? = null,
    errorMessage: String? = null,
    enableTextFieldSeparator: Boolean = false,
    @StringRes labelRes: Int,
    @StringRes placeHolderRes: Int,
    @DrawableRes leadingIconRes: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    onDone: ((FocusManager) -> Unit)? = null,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    with(MaterialTheme.colorScheme) {
        Column {
            OutlinedTextField(
                modifier = modifier,
                value = value.orEmpty(),
                prefix = prefix ?: if (enableTextFieldSeparator) {
                    {
                        BrownieTextFieldSeparator()
                    }
                } else {
                    null
                },
                onValueChange = onValueChanged,
                supportingText = supportingText?.invoke()?.let { text ->
                    {
                        AnimatedVisibility(text.isNotEmpty()) {
                            Text(text = text, fontFamily = montserratFontFamily)
                        }
                    }
                },
                label = { Text(text = stringResource(id = labelRes), fontFamily = montserratFontFamily) },
                placeholder = { Text(text = stringResource(id = placeHolderRes), fontFamily = montserratFontFamily) },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                shape = RoundedCornerShape(27.dp),
                colors = colors,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone?.invoke(focusManager) ?: run {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    }
                ),
                leadingIcon = leadingIconRes?.let {
                    {
                        BrownieImageIcon(
                            type = BrownieType.ICON,
                            iconRes = it,
                            tintColor =primary
                        )
                    }
                } ?: leadingIcon,
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, contentDescription = description, tint = primary)
                    }
                }
            )
            errorMessage?.let {
                BrownieText(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(250.dp),
                    type = BrownieTextTypeEnum.LABEL_MEDIUM,
                    titleText = it,
                    textColor = error
                )
            }
        }
    }
}