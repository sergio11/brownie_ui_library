package sanchez.sanchez.sergio.brownie.extension

import java.text.SimpleDateFormat
import java.util.*


fun Date.toStringFormat(dateFormat: String): String
        = SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)