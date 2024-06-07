package com.dreamsoftware.brownie.component.fab

import androidx.annotation.StringRes

/**
 * Represents an item for a Floating Action Button (FAB) with an icon and label.
 * */

data class BrownieFabButtonItem(
    val id: Int,
    val iconRes: Int,
    val label: String? = null,
    @StringRes val labelRes: Int? = null
)