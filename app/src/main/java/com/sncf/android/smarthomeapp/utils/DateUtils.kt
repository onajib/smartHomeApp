package com.sncf.android.smarthomeapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.Calendar

object DateUtils {
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm"
    private const val SIMPLE_DATE_FORMAT = "yyyy-MM-dd"

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
    private val formatterEN = SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH)

    fun initDatePicker(context: Context, editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                context,
                { _, y, m, dayOfMonth ->
                    calendar.set(Calendar.YEAR, y)
                    calendar.set(Calendar.MONTH, m)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val formatter =
                        SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.FRANCE)
                    editText.setText(formatter.format(calendar.time))
                },
                year,
                month,
                day
            ).show()
    }

    fun parse(date: Date): String {
        return LocalDate.parse(format(date), dateTimeFormatter).toString()
    }

    private fun format(date: Date): String {
        return formatterEN.format(date)
    }
}