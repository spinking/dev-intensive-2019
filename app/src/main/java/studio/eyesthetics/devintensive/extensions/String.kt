package studio.eyesthetics.devintensive.extensions

import studio.eyesthetics.devintensive.extensions.truncate
import studio.eyesthetics.devintensive.extensions.stripHtml

fun String.truncate(index: Int = 16) :String {
    val str = this.trim()
    return if(str.length < index + 2) str else str.substring(0, index + 1).trim () + "..."
}

fun String.stripHtml(): String {
    var str = this
    str = str.replace(Regex("<.*?>"), "")
    str = str.replace(Regex("&.*?;"), "")
    str = str.replace(Regex("\\s+"), " ")
    return str
}