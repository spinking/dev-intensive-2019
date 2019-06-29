package studio.eyesthetics.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

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
    var duration = (date.time - this.time)/1000
    var res: String = when(duration) {
        in 0..1 -> "только что"
        in 2..45 -> "несколько секунд назад"
        in 46..75 -> "минуту назад"
        in 76..45*60 -> "${duration/60000} минут назад"
        in 45*60 + 1..75*60 -> "час назад"
        in 75 * 60 + 1..22 * 60 * 60 -> "${duration/(60 * 60)} часов назад"
        in 22 * 60 * 60 + 1..26 * 60 * 60 -> "день назад"
        in 26 * 60 * 60 + 1..360 * 60 * 60 * 24 -> "${duration/ (60* 60 *24)} дней назад"
        else -> "более года назад"
    }
    return  res;
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}