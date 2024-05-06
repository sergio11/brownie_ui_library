package com.dreamsoftware.brownie.utils

import com.dreamsoftware.brownie.component.BrownieTabUi

fun <T>Iterable<BrownieTabUi<T>>.tabSelectedIndex() = indexOfFirst { it.isSelected }
fun <T>Iterable<BrownieTabUi<T>>.tabSelectedTitle() = find { it.isSelected }?.titleRes