package ru.skillbranch.devintensive.extensions

fun String.truncate(index: Int = 16) :String {
    val str = this.trim()
    return if(str.length < index + 1) str else str.substring(0, index).trim () + "..."
}

fun String.stripHtml(): String {
    var str = this
    str = str.replace(Regex("<.*?>"), "")
    str = str.replace(Regex("&.*?;"), "")
    str = str.replace(Regex("\\s+"), " ")
    return str
}