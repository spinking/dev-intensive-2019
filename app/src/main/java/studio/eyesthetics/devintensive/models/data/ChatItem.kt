package ru.skillbranch.devintensive.models.data

/**
 * Created by BashkatovSM on 26.08.2019
 */
data class ChatItem (
    val id: String,
    val avatar: String?,
    val initials: String,
    val title: String,
    val shortDescription: String?,
    val messageCount: Int = 0,
    val lastMessageDate: String?,
    val isOnline: Boolean = false,
    val chatType: ChatType = ChatType.SINGLE,
    val author: String? = null
)