package com.gillopa.newyear

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object CountdownCalculator {

    fun daysUntilNewYear(today: LocalDate = LocalDate.now()): Long {
        val target = LocalDate.of(today.year + 1, 1, 1)
        return ChronoUnit.DAYS.between(today, target)
    }

    fun targetYear(today: LocalDate = LocalDate.now()): Int = today.year + 1

    fun isNewYearsDay(today: LocalDate = LocalDate.now()): Boolean =
        today.monthValue == 1 && today.dayOfMonth == 1

    /** Russian plural: 1 день, 2–4 дня, 5–20 дней, 21 день, … */
    fun daysWord(days: Long): String {
        val n = days % 100
        val n1 = n % 10
        return when {
            n in 11L..14L -> "дней"
            n1 == 1L -> "день"
            n1 in 2L..4L -> "дня"
            else -> "дней"
        }
    }

    fun vibeLine(days: Long, today: LocalDate = LocalDate.now()): String {
        if (isNewYearsDay(today)) return "Пусть год будет тёплым и волшебным"
        return when {
            days <= 1L -> "Почти время загадывать желание"
            days <= 7L -> "Финишная прямая — запах мандаринов уже рядом"
            days <= 30L -> "Гирлянды уже мигают где-то впереди"
            days <= 90L -> "Зима стелет ковёр — праздник не за горами"
            else -> "Долгий путь к полночи, но он того стоит"
        }
    }
}
