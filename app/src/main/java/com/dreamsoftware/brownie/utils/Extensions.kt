package com.dreamsoftware.brownie.utils

import android.content.Context
import android.content.Intent
import com.dreamsoftware.brownie.component.BrownieTabUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.provider.Settings
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun <T> Iterable<BrownieTabUi<T>>.tabSelectedIndex() = indexOfFirst { it.isSelected }
fun <T> Iterable<BrownieTabUi<T>>.tabSelectedTitle() = find { it.isSelected }?.titleRes

val String.Companion.EMPTY: String
    get() = ""

val Char.Companion.SPACE: Char
    get() = ' '

fun <T> StateFlow<T>.toMutable() = this as MutableStateFlow

fun Context.openSystemSettings() {
    startActivity(Intent(Settings.ACTION_SETTINGS))
}

/**
 * Formats a [Date] object into a [String] using the specified pattern.
 *
 * @param pattern The format pattern to apply (default: "yyyy-MM-dd").
 * @return The formatted date string.
 */
fun Date.formatToString(pattern: String = "yyyy-MM-dd"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

/**
 * Parses a date string into a [Date] object using the specified pattern.
 *
 * @param pattern The format pattern to apply (default: "yyyy-MM-dd").
 * @return The parsed [Date] object, or null if parsing fails.
 * @throws IllegalArgumentException If the date string is invalid.
 */
fun String.toDate(pattern: String = "yyyy-MM-dd"): Date? =
    runCatching {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.parse(this)
    }.getOrNull()


fun Modifier.drawIndicators(vararg indicators: IndicatorConfig): Modifier = this.drawBehind {
    indicators.forEach { indicator ->
        if (indicator.shouldDraw) {
            drawCircle(
                color = indicator.color,
                radius = size.maxDimension * indicator.radiusFraction,
                style = indicator.style
            )
        }
    }
}

data class IndicatorConfig(
    val shouldDraw: Boolean,
    val color: Color,
    val radiusFraction: Float = 0.5f,
    val style: DrawStyle = Fill
)