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

fun String.validUrl(): Boolean {
    val address = this.substringBeforeLast("/").toLowerCase()
    var username = this.substringAfterLast("/").toLowerCase()

    fun validAddress(address: String) : Boolean {
        return true
    }

    fun validUserName(username: String) : Boolean {
        return true
    }
    if (username == address) username = ""

    return when {
        this == "" -> true
        validAddress(address) && validUserName(username) -> true
        else -> false
    }
}
