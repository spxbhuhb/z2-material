package hu.simplexion.z2.browser.calendar

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import kotlinx.datetime.*

fun Z2.year(year: Int, startDay: DayOfWeek) =
    div("calendar-year") {
        for (month in Month.values()) {
            month(MonthConfig(year, month, Clock.System.todayIn(TimeZone.currentSystemDefault()), startDay, listOf(LocalDate(year, month, 12))))
        }
    }

fun Z2.month(config: MonthConfig) =
    div("year-month") {

        div("month-name", "title-small") {
            text { config.month.name }
        }

        div("month-days") {
            var day = LocalDate(config.year, config.month, 1)

            // find the first day to display, this may be in the previous month
            while (day.dayOfWeek != config.firstWeekDay) {
                day = day.minus(1, DateTimeUnit.DAY)
            }

            // header with the day names
            var weekDay = day

            for (i in 1..7) {
                div("month-day-letter", "body-small") {
                    text { weekDay.dayOfWeek.name.first() }
                }
                weekDay = weekDay.plus(1, DateTimeUnit.DAY)
            }

            // add weeks
            for (i in 1..6) {
                for (j in 1..7) {
                    div("month-day", "body-small", dayClass(day, config)) {
                        if (day in config.markedDays) addClass("marked")
                        text { day.dayOfMonth }
                    }
                    day = day.plus(1, DateTimeUnit.DAY)
                }
            }
        }
    }

fun dayClass(day: LocalDate, config: MonthConfig): String =
    when {
        day.month == config.month && day == config.today -> "today"
        day.month == config.month -> "this"
        else -> "other"
    }