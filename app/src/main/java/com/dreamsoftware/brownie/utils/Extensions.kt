package com.dreamsoftware.brownie.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.dreamsoftware.brownie.component.BrownieTabUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T>Iterable<BrownieTabUi<T>>.tabSelectedIndex() = indexOfFirst { it.isSelected }
fun <T>Iterable<BrownieTabUi<T>>.tabSelectedTitle() = find { it.isSelected }?.titleRes

val String.Companion.EMPTY: String
    get() = ""

val Char.Companion.SPACE: Char
    get() = ' '

fun <T> StateFlow<T>.toMutable() = this as MutableStateFlow


fun Modifier.clickWithRipple(bounded: Boolean = true, onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = bounded),
        onClick = { onClick() }
    )
}