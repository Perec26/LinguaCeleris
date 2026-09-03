package com.linguaceleris.time

import kotlinx.datetime.LocalDate

fun findLatestDate(vararg dates: LocalDate?): LocalDate? {
    if (dates.all { it == null }) return null
    return dates.filterNotNull()
        .reduce { latest, date ->
            if (date.isAfter(latest)) date else latest
        }
}

fun LocalDate.isAfter(date: LocalDate) = this.toEpochDays() > date.toEpochDays()
