package sanchez.sanchez.sergio.brownie.extension

import android.content.Context
import sanchez.sanchez.sergio.brownie.R
import java.text.SimpleDateFormat
import java.util.*


fun Long.toDateTimeString(appContext: Context): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return SimpleDateFormat(appContext.getString(R.string.datetime_format)).format(calendar.time)
}

fun Long.toDateString(appContext: Context): String {
    val calendar = Calendar.getInstance().also {
        it.timeInMillis = this
    }
    return SimpleDateFormat(appContext.getString(R.string.date_format)).format(calendar.time)
}

fun Long.toDateStringWithAge(appContext: Context): String {
    val calendar = Calendar.getInstance().also {
        it.timeInMillis = this
    }

    return appContext.getString(R.string.date_format_with_age,
        SimpleDateFormat(appContext.getString(R.string.date_format), Locale.getDefault())
            .format(calendar.time),
        getAge()
        )
}

fun Long.getAge(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    val today = Calendar.getInstance()
    var age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
    if (today.get(Calendar.MONTH) < calendar.get(Calendar.MONTH) || today.get(Calendar.MONTH) == calendar.get(
            Calendar.MONTH
        ) && today.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH)
    ) {
        age--
    }
    return age
}

fun Long.toDateTime(): Date{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.time
}

fun Long.ToHoursMinutesSecondsFormat(appContext: Context): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format(appContext.getString(R.string.hours_minutes_seconds_format), hours, minutes, seconds)
}