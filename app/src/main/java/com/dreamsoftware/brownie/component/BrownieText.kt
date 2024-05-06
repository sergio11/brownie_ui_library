package com.dreamsoftware.brownie.component

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.dreamsoftware.brownie.R

private const val DEFAULT_MAX_LINES = Int.MAX_VALUE

enum class BrownieTextTypeEnum {
    TITLE_LARGE,
    TITLE_MEDIUM,
    TITLE_SMALL,
    LABEL_LARGE,
    LABEL_MEDIUM,
    LABEL_SMALL,
    BODY_SMALL,
    BODY_MEDIUM,
    BODY_LARGE,
    HEADLINE_SMALL,
    HEADLINE_MEDIUM,
    HEADLINE_LARGE
}

@Composable
fun BrownieText(
    modifier: Modifier = Modifier,
    type: BrownieTextTypeEnum,
    @StringRes titleRes: Int? = null,
    titleText: String? = null,
    singleLine: Boolean = false,
    textBold: Boolean = false,
    maxLines: Int = DEFAULT_MAX_LINES,
    textColor: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign? = null
) {
    CommonTextComponent(
        modifier = modifier,
        singleLine = singleLine,
        text = titleRes?.let {
            stringResource(id = it)
        } ?: titleText ?: stringResource(id = R.string.no_text_value),
        maxLines = maxLines,
        textColor = textColor,
        textAlign = textAlign,
        textBold = textBold,
        textStyle = with(MaterialTheme.typography) {
            when (type) {
                BrownieTextTypeEnum.TITLE_LARGE -> titleLarge
                BrownieTextTypeEnum.TITLE_MEDIUM -> titleMedium
                BrownieTextTypeEnum.TITLE_SMALL -> titleSmall
                BrownieTextTypeEnum.LABEL_LARGE -> labelLarge
                BrownieTextTypeEnum.LABEL_MEDIUM -> labelMedium
                BrownieTextTypeEnum.LABEL_SMALL -> labelSmall
                BrownieTextTypeEnum.BODY_SMALL -> bodySmall
                BrownieTextTypeEnum.BODY_MEDIUM -> bodyMedium
                BrownieTextTypeEnum.BODY_LARGE -> bodyLarge
                BrownieTextTypeEnum.HEADLINE_SMALL -> headlineSmall
                BrownieTextTypeEnum.HEADLINE_MEDIUM -> headlineMedium
                BrownieTextTypeEnum.HEADLINE_LARGE -> headlineLarge
            }
        }
    )
}

@Composable
private fun CommonTextComponent(
    modifier: Modifier = Modifier,
    text: String,
    singleLine: Boolean,
    maxLines: Int,
    textColor: Color,
    textBold: Boolean,
    textAlign: TextAlign?,
    textStyle: TextStyle
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        color = textColor,
        style = textStyle,
        fontWeight = if (textBold) {
            FontWeight.Bold
        } else {
            null
        },
        maxLines = if (singleLine) {
            1
        } else {
            maxLines
        }
    )
}