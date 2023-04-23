package com.sosyal.app.util

import com.sosyal.app.domain.model.DateTimeInfo
import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    fun convertStringToDateOrTime(date: String): DateTimeInfo {
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val currentDate = Calendar.getInstance().time
        val postDate = dateTimeFormatter.parse(date)

        val diff = currentDate.time - postDate!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val formattedPostDate = dateFormatter.format(postDate)

        return DateTimeInfo(
            seconds = seconds,
            minutes = minutes,
            hours = hours,
            days = days,
            date = formattedPostDate
        )
    }

    fun convertDateToString(date: Date): String {
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        return dateTimeFormatter.format(date)
    }
}
