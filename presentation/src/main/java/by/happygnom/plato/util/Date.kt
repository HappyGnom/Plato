package by.happygnom.plato.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedDateString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(this)
}

fun Date.toFormattedTimeString(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(this)
}


fun Date.toFormattedDateTimeString(): String {
    val dateTimeFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return dateTimeFormat.format(this)
}
