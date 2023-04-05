package com.sosyal.app.util

import com.sosyal.app.domain.model.DateTimeInfo
import java.text.SimpleDateFormat
import java.util.*

object Formatter {
    fun convertToDateOrTime(date: String): DateTimeInfo {
        val localeID = Locale(Locale.getDefault().displayLanguage, "ID")
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", localeID)
        val dateFormatter = SimpleDateFormat("dd MMM yyyy", localeID)

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
}
