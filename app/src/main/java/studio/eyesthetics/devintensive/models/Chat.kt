package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.utils.Utils
import java.util.*


/**
 * Created by BashkatovSM on 28.06.2019
 */
class Chat(
    val id:String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    fun unreadableMessageCount(): Int {
        //TODO implement me
        return 0
    }

    private fun lastMessageDate(): Date? {
        //TODO implement me
        return Date()
    }

    private fun lastMessageShort(): String {
        //TODO implement me
        return "Сообщений нет"
    }

    private fun isSingle(): Boolean = members.size == 1
    fun toChatItem(): ChatItem {
        if(isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort(),
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        }
    }
}
