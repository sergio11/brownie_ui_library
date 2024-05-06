package com.dreamsoftware.brownie.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T: Any>BrownieLazyColumn(
    state: LazyListState,
    items: Iterable<T>,
    onBuildContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        state = state,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items.count()) { index ->
            onBuildContent(items.elementAt(index))
        }
    }
}