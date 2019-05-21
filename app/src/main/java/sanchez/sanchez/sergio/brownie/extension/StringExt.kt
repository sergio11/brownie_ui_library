package sanchez.sanchez.sergio.brownie.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.Companion.empty() = ""

/**
 * To Date Time
 */
fun String.ToDateTime(dateFormat: String): Date = SimpleDateFormat(dateFormat, Locale.getDefault()).parse(this)

/**
 * To Int Array
 */
fun String.toIntArray(): IntArray? {
    var result: IntArray? = null
    try {
        result = this.split(",")
            .map { it.toInt() }
            .toIntArray()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return result
}