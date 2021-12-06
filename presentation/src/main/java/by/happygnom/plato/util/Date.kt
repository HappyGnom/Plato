package by.happygnom.plato.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(this)
}
