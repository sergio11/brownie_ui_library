package com.dreamsoftware.brownie.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BrownieTabUi<out T>(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    var isSelected: Boolean = false,
    val type: T
)