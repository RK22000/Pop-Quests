package io.github.rk22000.design_systems.ui

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.datepicker.RangeDateSelector
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun DatePicker(
    context: Context,
    date: Long,
    onDateChange: (Long)->Unit,
) {
    val localDate = LocalDate.ofEpochDay(date)
    DatePickerDialog(
        context,
        object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                onDateChange(LocalDate.of(p1, p2+1, p3).toEpochDay())
            }

        },
        localDate.year,
        localDate.monthValue-1,
        localDate.dayOfMonth
    ).show()
}