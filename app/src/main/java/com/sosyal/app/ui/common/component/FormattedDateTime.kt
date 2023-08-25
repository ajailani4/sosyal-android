package com.sosyal.app.ui.common.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sosyal.app.R
import com.sosyal.app.domain.model.DateTimeInfo

@Composable
fun formattedDateTime(dateTimeInfo: DateTimeInfo): String {
    return dateTimeInfo.run {
        when {
            seconds in (0..59) -> {
                stringResource(id = R.string.just_now)
            }

            minutes in (1..59) -> {
                stringResource(
                    id = if (minutes > 1L) {
                        R.string.minutes_ago
                    } else {
                        R.string.minute_ago
                    },
                    minutes
                )
            }

            hours in (1..23) -> {
                stringResource(
                    id = if (hours > 1L) {
                        R.string.hours_ago
                    } else {
                        R.string.hour_ago
                    },
                    hours
                )
            }

            days == 1L -> stringResource(id = R.string.yesterday)

            else -> date
        }
    }
}