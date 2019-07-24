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

fun String.validUrl(): String {
    return when {
        this.contains(Regex("^http://")) -> this
        this.contains(Regex("^www")) -> this
        this.contains(Regex("^http://www")) -> this
        this.contains(Regex("github.com")) -> this
        !this.contains(Regex("enterprise")) -> this
        !this.contains(Regex("features")) -> this
        !this.contains(Regex("topics")) -> this
        !this.contains(Regex("collections")) -> this
        !this.contains(Regex("trending")) -> this
        !this.contains(Regex("events")) -> this
        !this.contains(Regex("marketplace")) -> this
        !this.contains(Regex("pricing")) -> this
        !this.contains(Regex("nonprofit")) -> this
        !this.contains(Regex("customer-stories")) -> this
        !this.contains(Regex("security")) -> this
        !this.contains(Regex("login")) -> this
        !this.contains(Regex("join")) -> this
        else -> "Невалидный адрес репозитория"
    }
}

/*
fun String.validUrl(): Boolean {
    val address = this.substringBeforeLast("/").toLowerCase()
    var username = this.substringAfterLast("/").toLowerCase()

    fun validAddress(address: String): Boolean {
        val invalidNames = listOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "marketplace",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "join")
        return when
    }

    if(username == address) username = ""

    return when {
        this == "" -> true
        validAddress(address) && validUserName(username) -> true
        else -> false
    }
    
    fun iAddressValid(address: String): Boolean {}
}
*/
