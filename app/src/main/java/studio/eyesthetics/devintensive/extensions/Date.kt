package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.R
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd:MM:yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var duration = (date.time - this.time)
    var res: String = when(duration) {
        in 0 * SECOND..1 * SECOND -> "только что"
        in (1 * SECOND + 1)..45 * SECOND  -> "несколько секунд назад"
        in (45 * SECOND + 1)..75 * SECOND -> "минуту назад"
        in (75 * SECOND + 1)..45 * MINUTE -> "${minuteAsPlurals(duration / MINUTE)} назад"
        in (45* MINUTE + 1)..75 * MINUTE -> "час назад"
        in (75 * MINUTE + 1)..22 * HOUR -> "${hourAsPlurals(duration/ HOUR)} назад"
        in (22 * HOUR + 1)..26 * HOUR -> "день назад"
        in (26 * HOUR + 1)..360 * DAY -> "${dayAsPlurals(duration / DAY)} назад"

        in -45 * SECOND..(0 * SECOND - 1) -> "через несколько секунд"
        in -75 * SECOND..(-45 * SECOND - 1) -> "через минуту"
        in -45 * MINUTE..(-75 * SECOND - 1) -> "через ${minuteAsPlurals((duration / MINUTE) * -1)}"
        in -75 * MINUTE..(-45 * MINUTE - 1) -> "через час"
        in -22 * HOUR..(-75 * MINUTE - 1) -> "через ${hourAsPlurals((duration / HOUR) * -1)}"
        in -26 * HOUR..(-22 * HOUR - 1) -> "через день"
        in -360 * DAY..(-26 * HOUR - 1) -> "через ${dayAsPlurals((duration / DAY) * -1)}"

        else -> if(duration / DAY > -360) "более года назад" else "более чем через год"
    }
    return  res
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

private fun minuteAsPlurals(value: Long) = when(value.asPlurals) {
    Plurals.ONE -> "$value минуту"
    Plurals.FEW -> "$value минуты"
    Plurals.MANY -> "$value минут"
}

private fun hourAsPlurals(value: Long) = when(value.asPlurals) {
    Plurals.ONE -> "$value час"
    Plurals.FEW -> "$value часа"
    Plurals.MANY -> "$value часов"
}

private fun dayAsPlurals(value: Long) = when(value.asPlurals) {
    Plurals.ONE -> "$value день"
    Plurals.FEW -> "$value дня"
    Plurals.MANY -> "$value дней"
}

private fun yearAsPlurals(value: Long) = when(value.asPlurals) {
    Plurals.ONE -> "$value год"
    Plurals.FEW -> "$value года"
    Plurals.MANY -> "$value лет"
}

val Long.asPlurals
    get() = when {
        this in 5L..20L -> Plurals.MANY
        this % 10L == 1L -> Plurals.ONE
        this % 10L in 2L..4L -> Plurals.FEW
        else -> Plurals.MANY
    }

enum class Plurals {
    ONE,
    FEW,
    MANY
}