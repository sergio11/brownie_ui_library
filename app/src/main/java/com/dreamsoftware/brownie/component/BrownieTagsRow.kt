package com.dreamsoftware.brownie.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.theme.montserratFontFamily

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrownieTagsRow(
    modifier: Modifier = Modifier,
    tagList: List<String>?,
    isReadOnly: Boolean = false,
    onDeleteClicked: (tag: String) -> Unit = {},
    content: @Composable () -> Unit = {}) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        tagList?.let { tags ->
            repeat(tags.size) {
                TagInput(text = tags[it], isReadOnly) {
                    onDeleteClicked(tags[it])
                }
            }
        }
        content()
    }
}

@Composable
private fun TagInput(
    text: String,
    isReadOnly: Boolean = false,
    onDeleteClicked: () -> Unit = {}
) {
    InputChip(
        modifier = Modifier.padding(horizontal = 4.dp),
        selected = false,
        onClick = {},
        colors = InputChipDefaults.inputChipColors(containerColor = MaterialTheme.colorScheme.primary),
        label = { Text(text.trim(), fontFamily = montserratFontFamily,
            textAlign = TextAlign.Center) },
        trailingIcon = {
            if(!isReadOnly) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(InputChipDefaults.IconSize)
                        .clickable { onDeleteClicked() }
                )
            }
        }
    )
}