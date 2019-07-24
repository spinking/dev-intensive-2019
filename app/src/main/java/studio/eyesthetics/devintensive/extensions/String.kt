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
        val validAddresses = listOf(
            "https://www.github.com",
            "https://github.com",
            "www.github.com",
            "github.com"
        )
        return when {
            validAddresses.any{ it == address} -> true
            else -> false
        }
    }

    fun validUserName(username: String) : Boolean {
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

        return when {
            invalidNames.any{ it == username} -> false
            username.startsWith(" ") -> false
            username.contains(Regex("[^a-zA-Z0-9-]")) -> false
            username.startsWith("-") || username.endsWith("-") -> false
            else -> true
        }
    }
    if (username == address) username = ""

    return when {
        this == "" -> true
        validAddress(address) && validUserName(username) -> true
        else -> false
    }
}
