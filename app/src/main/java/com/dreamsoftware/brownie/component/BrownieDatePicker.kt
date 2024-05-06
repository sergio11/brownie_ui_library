package com.dreamsoftware.brownie.component

import android.app.DatePickerDialog
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val BrownieDatePickerModifier = Modifier
    .padding(vertical = 20.dp)
    .width(300.dp)

@Composable
fun BrownieDatePicker(
    modifier: Modifier = BrownieDatePickerModifier,
    @StringRes labelRes: Int,
    @StringRes placeHolderRes: Int,
    value: String? = null,
    onValueChange: (String) -> Unit = {},
    pattern: String = "yyyy-MM-dd",
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (!value.isNullOrBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth).toString())
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )
    BrownieDefaultTextField(
        labelRes = labelRes,
        placeHolderRes = placeHolderRes,
        isEnabled = false,
        value = value,
        modifier = Modifier.clickable { dialog.show() }.then(modifier),
    )
}