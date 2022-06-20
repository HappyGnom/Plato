package by.happygnom.plato.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import by.happygnom.plato.R
import java.util.*

fun showDatePickerDialog(
    context: Context,
    initialDate: Calendar = Calendar.getInstance(),
    callback: (calendar: Calendar) -> Unit
) {
    DatePickerDialog(
        context, R.style.PlatoDatePickerStyle,
        { _, year: Int, month: Int, dayOfMonth: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            callback(calendar)
        },
        initialDate.get(Calendar.YEAR),
        initialDate.get(Calendar.MONTH),
        initialDate.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun showTimePickerDialog(
    context: Context,
    initialDate: Calendar = Calendar.getInstance(),
    callback: (calendar: Calendar) -> Unit
) {
    TimePickerDialog(
        context, R.style.PlatoDatePickerStyle,
        { _, hours: Int, minutes: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(0, 0, 0, hours, minutes)

            callback(calendar)
        },
        initialDate.get(Calendar.HOUR_OF_DAY),
        initialDate.get(Calendar.MINUTE),
        true
    ).show()
}
