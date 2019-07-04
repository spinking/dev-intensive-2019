package ru.skillbranch.devintensive.models

import studio.eyesthetics.devintensive.extensions.truncate
import studio.eyesthetics.devintensive.extensions.stripHtml

/**
 * Created by BashkatovSM on 28.06.2019
 */
class Chat(
    val id:String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {
}