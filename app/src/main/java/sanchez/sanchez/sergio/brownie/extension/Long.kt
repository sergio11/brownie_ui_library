package sanchez.sanchez.sergio.brownie.extension

import android.content.Context
import sanchez.sanchez.sergio.brownie.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * To Date Time String
 */
fun Long.toDateTimeString(appContext: Context): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return SimpleDateFormat(appContext.getString(R.string.datetime_format)).format(calendar.time)
}

/**
 * To Date
 */
fun Long.toDateTime(): Date{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.time
}

/**
 * To Hours Minutes Seconds Format
 */
fun Long.ToHoursMinutesSecondsFormat(appContext: Context): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format(appContext.getString(R.string.hours_minutes_seconds_format), hours, minutes, seconds)
}