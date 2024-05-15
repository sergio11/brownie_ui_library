package com.dreamsoftware.brownie.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.theme.Dimens.DEFAULT_BUTTON_LARGE_HEIGHT
import com.dreamsoftware.brownie.theme.Dimens.DEFAULT_BUTTON_LARGE_WIDTH
import com.dreamsoftware.brownie.theme.Dimens.DEFAULT_BUTTON_MEDIUM_HEIGHT
import com.dreamsoftware.brownie.theme.Dimens.DEFAULT_BUTTON_MEDIUM_WIDTH
import com.dreamsoftware.brownie.theme.Dimens.DEFAULT_BUTTON_SMALL_HEIGHT
import com.dreamsoftware.brownie.theme.Dimens.DEFAULT_BUTTON_SMALL_WIDTH

enum class BrownieButtonTypeEnum {
    SMALL,
    MEDIUM,
    LARGE
}

enum class BrownieButtonStyleTypeEnum {
    NORMAL,
    INVERSE,
    TRANSPARENT
}

@Composable
fun  BrownieButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    type: BrownieButtonTypeEnum = BrownieButtonTypeEnum.MEDIUM,
    style: BrownieButtonStyleTypeEnum = BrownieButtonStyleTypeEnum.NORMAL,
    enableBorder: Boolean = true,
    buttonShape: Shape = RoundedCornerShape(percent = 50),
    text: String? = null,
    textRes: Int? = null,
    onClick: () -> Unit,
) {
    with(MaterialTheme.colorScheme) {
        Button(
            onClick = onClick,
            modifier = modifier.then(
                Modifier
                    .width(
                        when (type) {
                            BrownieButtonTypeEnum.SMALL -> DEFAULT_BUTTON_SMALL_WIDTH
                            BrownieButtonTypeEnum.MEDIUM -> DEFAULT_BUTTON_MEDIUM_WIDTH
                            BrownieButtonTypeEnum.LARGE -> DEFAULT_BUTTON_LARGE_WIDTH
                        }
                    )
                    .height(
                        when (type) {
                            BrownieButtonTypeEnum.SMALL -> DEFAULT_BUTTON_SMALL_HEIGHT
                            BrownieButtonTypeEnum.MEDIUM -> DEFAULT_BUTTON_MEDIUM_HEIGHT
                            BrownieButtonTypeEnum.LARGE -> DEFAULT_BUTTON_LARGE_HEIGHT
                        }
                    )
                    .border(
                        width = if (enableBorder) {
                            2.dp
                        } else {
                            0.dp
                        },
                        color = if(enableBorder){
                            when(style) {
                                BrownieButtonStyleTypeEnum.NORMAL -> primary
                                BrownieButtonStyleTypeEnum.INVERSE -> secondary
                                BrownieButtonStyleTypeEnum.TRANSPARENT -> surface
                            }
                        } else {
                            Color.Transparent
                        },
                        shape = buttonShape
                    )
                    .clip(buttonShape)
            ),
            enabled = enabled,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = when(style) {
                    BrownieButtonStyleTypeEnum.NORMAL -> if (enableBorder) {
                        primary.copy(alpha = 0.8f)
                    } else {
                        primary
                    }
                    BrownieButtonStyleTypeEnum.INVERSE -> if (enableBorder) {
                        secondary.copy(alpha = 0.8f)
                    } else {
                        secondary
                    }
                    BrownieButtonStyleTypeEnum.TRANSPARENT -> Color.Transparent
                },
                contentColor = when(style) {
                    BrownieButtonStyleTypeEnum.NORMAL -> onPrimaryContainer
                    BrownieButtonStyleTypeEnum.INVERSE -> onSecondaryContainer
                    BrownieButtonStyleTypeEnum.TRANSPARENT -> surface
                }
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                BrownieText(
                    type = when(type) {
                        BrownieButtonTypeEnum.SMALL -> BrownieTextTypeEnum.BODY_SMALL
                        BrownieButtonTypeEnum.MEDIUM -> BrownieTextTypeEnum.BODY_MEDIUM
                        BrownieButtonTypeEnum.LARGE -> BrownieTextTypeEnum.BODY_LARGE
                    },
                    titleText = text,
                    titleRes = textRes,
                    textColor = when(style) {
                        BrownieButtonStyleTypeEnum.NORMAL -> onPrimaryContainer
                        BrownieButtonStyleTypeEnum.INVERSE -> onSecondaryContainer
                        BrownieButtonStyleTypeEnum.TRANSPARENT -> surface
                    },
                    textAlign = TextAlign.Center,
                    textBold = true
                )
            }
        }
    }
}