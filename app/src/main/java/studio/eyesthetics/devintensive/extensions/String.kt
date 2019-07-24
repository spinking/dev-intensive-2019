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
        val protocols = listOf(
            "https://www.github.com",
            "https://github.com",
            "www.github.com",
            "github.com"
        )
        return  protocols.any { it == address }
    }

    fun validUserName(username: String) : Boolean {
        val excludePath = listOf(
            "",
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

        return !(excludePath.any{ it == username} || username.contains(Regex("[^\\w]")))
    }
    if (username == address) username = ""

    return this == "" || (validAddress(address) && validUserName(username))
}
