package com.dreamsoftware.brownie.component.fab

import androidx.compose.ui.graphics.Color

interface BrownieFabButtonSub {
    val iconTint: Color
    val backgroundTint: Color
}

/**
 * Implementation of [BrownieFabButtonSub] interface.
 *
 * @property iconTint The [Color] used to tint the icon of the sub-item.
 * @property backgroundTint The [Color] used to tint the background of the sub-item.
 */
private class BrownieFabButtonSubImpl(
    override val iconTint: Color,
    override val backgroundTint: Color
) : BrownieFabButtonSub

/**
 * Creates a new instance of [BrownieFabButtonSub] with the provided icon and background tints.
 *
 * @param backgroundTint The [Color] used to tint the background of the sub-item.
 * @param iconTint The [Color] used to tint the icon of the sub-item.
 * @return A new instance of [BrownieFabButtonSub] with the specified icon and background tints.
 */
fun BrownieFabButtonSub(
    backgroundTint: Color,
    iconTint: Color = Color.White
): BrownieFabButtonSub = BrownieFabButtonSubImpl(iconTint, backgroundTint)