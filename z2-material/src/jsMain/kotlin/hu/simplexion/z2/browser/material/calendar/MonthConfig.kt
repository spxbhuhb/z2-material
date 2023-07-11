package hu.simplexion.z2.browser.material.calendar

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

class MonthConfig(
    val year : Int,
    val month: Month,
    val today: LocalDate,
    val firstWeekDay : DayOfWeek,
    val markedDays : List<LocalDate>
)