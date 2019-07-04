package ru.skillbranch.devintensive.models

import studio.eyesthetics.devintensive.extensions.truncate
import studio.eyesthetics.devintensive.extensions.stripHtml

class UserView(
    val id:String,
    val fullName: String,
    val nickName: String,
    var avatar: String? = null,
    var status: String? = "offline",
    val initials: String?
) {
    fun printMe() {
        println("""
            id: $id
            fullName: $fullName
            nickName: $nickName
            avatar: $avatar
            status: $status
            initials: $initials
        """.trimIndent())
    }
}