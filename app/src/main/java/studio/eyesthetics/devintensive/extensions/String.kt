package studio.eyesthetics.devintensive.extensions

import android.text.Html

fun String.truncate(index: Int = 16): String {
    if(this.length < index) return this.trim()
    var str = this.substring(0, index)
    if(str.takeLast(length) == " ") str = str.substring(0, length - 1)
    return "${this.substring(0, index)}..."
}

fun String.stripHtml(): String {
    var str = this
    str = str.replace(Regex("<.*?>"), "")
    str = str.replace(Regex("&.*?;"), "")
    str = str.replace(Regex("\\s+"), " ")
    return str
}