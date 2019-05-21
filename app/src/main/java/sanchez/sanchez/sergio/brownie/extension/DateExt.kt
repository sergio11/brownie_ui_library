package sanchez.sanchez.sergio.brownie.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * To String Format
 */
fun Date.toStringFormat(dateFormat: String): String = SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)