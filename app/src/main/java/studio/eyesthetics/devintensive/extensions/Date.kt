package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.R
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
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

val Int.sec get() = this * SECOND
val Int.min get() = this * MINUTE
val Int.hour get() = this * HOUR
val Int.day get() = this * DAY

val Long.asMin get() = this.absoluteValue / MINUTE
val Long.asHour get() = this.absoluteValue / HOUR
val Long.asDay get() = this.absoluteValue / DAY

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = ((date.time + 200) / 1000 - (time + 200) / 1000) * 1000

    return if (diff >= 0) {
        when (diff) {
            in 0.sec..1.sec -> "только что"
            in 2.sec..45.sec -> "несколько секунд назад"
            in 46.sec..75.sec -> "минуту назад"
            in 76.sec..45.min -> "${minuteAsPlurals(diff.asMin)} назад"
            in 46.min..75.min -> "час назад"
            in 76.min..22.hour -> "${hourAsPlurals(diff.asHour)} назад"
            in 23.hour..26.hour -> "день назад"
            in 27.hour..360.day -> "${dayAsPlurals(diff.asDay)} назад"
            else -> "более года назад"
        }
    } else {
        when (diff) {
            in (-45).sec..0.sec -> "через несколько секунд"
            in (-75).sec..(-45).sec -> "через минуту"
            in (-45).min..(-75).sec -> "через ${minuteAsPlurals(diff.asMin)}"
            in (-75).min..(-45).min -> "через час"
            in (-22).hour..(-75).min -> "через ${hourAsPlurals(diff.asHour)}"
            in (-26).hour..(-22).hour -> "через день"
            in (-360).day..(-26).hour -> "через ${dayAsPlurals(diff.asDay)}"
            else -> "более чем через год"
        }
    }
}

/*fun Date.humanizeDiff(date: Date = Date()): String {
    //var duration = (date.time - this.time)
    val duration = ((date.time + 200) / 1000 - (time + 200) / 1000) * 1000
    var res: String = when(duration) {
        in 0.sec..1.sec -> "только что"
        in 1.sec ..45.sec  -> "несколько секунд назад"
        in 45.sec..75.sec -> "минуту назад"
        in 75.sec..45.min -> "${minuteAsPlurals(duration / MINUTE)} назад"
        in 45.min..75.min -> "час назад"
        in 75.min..22.hour -> "${hourAsPlurals(duration/ HOUR)} назад"
        in 22.hour..26.hour -> "день назад"
        in 26.hour..360 * DAY -> "${dayAsPlurals(duration / DAY)} назад"

        in (-45).sec..0.sec -> "через несколько секунд"
        in (-75).sec..-45.sec -> "через минуту"
        in (-45).sec..-75.sec -> "через ${minuteAsPlurals((duration / MINUTE) * -1)}"
        in (-75).sec..-45.min -> "через час"
        in (-22) * HOUR..(-75 .min - 1) -> "через ${hourAsPlurals((duration / HOUR) * -1)}"
        in -26 * HOUR..(-22 * HOUR - 1) -> "через день"
        in -360 * DAY..(-26 * HOUR - 1) -> "через ${dayAsPlurals((duration / DAY) * -1)}"

        else -> if(duration / DAY > -360) "более года назад" else "более чем через год"
    }
    return  res
}*/

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

        this % 100L in 5L..20L -> Plurals.MANY
        this % 10L == 1L -> Plurals.ONE
        this % 10L in 2L..4L -> Plurals.FEW

        else -> Plurals.MANY
    }

enum class Plurals {
    ONE,
    FEW,
    MANY
}