package studio.eyesthetics.devintensive.extensions

import android.text.Html

fun String.truncate(index: Int = 16): String {
    var str = this
    if(str.length < index) {
        if(str.takeLast(1) == " ") return this.dropLast(1)
        else return  this
    }
    str = this.substring(0, index)
    if(str.takeLast(1) == " ") str = str.dropLast(1)
    return "${this.substring(0, index)}..."
}

fun String.stripHtml(): String {
    var str = this
    str = str.replace(Regex("<.*?>"), "")
    str = str.replace(Regex("&.*?;"), "")
    str = str.replace(Regex("\\s+"), " ")
    return str
}