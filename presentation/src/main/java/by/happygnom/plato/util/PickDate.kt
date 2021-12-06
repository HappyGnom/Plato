package by.happygnom.plato.util

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
