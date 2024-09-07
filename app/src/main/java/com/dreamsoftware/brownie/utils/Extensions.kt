package com.dreamsoftware.brownie.utils

import android.content.Context
import android.content.Intent
import com.dreamsoftware.brownie.component.BrownieTabUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.provider.Settings

fun <T>Iterable<BrownieTabUi<T>>.tabSelectedIndex() = indexOfFirst { it.isSelected }
fun <T>Iterable<BrownieTabUi<T>>.tabSelectedTitle() = find { it.isSelected }?.titleRes

val String.Companion.EMPTY: String
    get() = ""

val Char.Companion.SPACE: Char
    get() = ' '

fun <T> StateFlow<T>.toMutable() = this as MutableStateFlow

fun Context.openSystemSettings() {
    startActivity(Intent(Settings.ACTION_SETTINGS))
}