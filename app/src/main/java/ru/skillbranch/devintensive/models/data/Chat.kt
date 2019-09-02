package ru.skillbranch.devintensive.models.data

import android.util.Log
import androidx.annotation.VisibleForTesting
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    companion object {
        fun archivedToChatItem(chats: List<Chat>): ChatItem{
            Log.d("M_Chat", "run archivedToChatItem")
            val lastChat = chats.sortedBy { it.lastMessageDate() }.last()
            return  ChatItem(
                "-1",
                "",
                "",
                "Архив чатов",
                lastChat.lastMessageShort().first,
                chats.sumBy { it.unreadableMessageCount() },
                lastChat.lastMessageDate()?.shortFormat(),
                false,
                ChatType.ARCHIVE,
                lastChat.lastMessageShort().second
            )
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    /*fun unreadableMessageCount(): Int {
        val unread = messages.map { a -> ((a as TextMessage).isReaded).not() }
        return unread.size
    }*/
    fun unreadableMessageCount(): Int {
        return messages.count{ !it.isReaded }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageDate(): Date? {
        //Последняя дата в списке сообщений
        return messages.lastOrNull()?.date
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageShort(): Pair<String, String?> {
        if (messages.isEmpty()) return "Сообщений нет" to "@John_Doe"

        val lastMessage = messages.last()

        return lastMessage.shortMessage() to "${lastMessage.from.firstName}"
    }
    /*fun lastMessageShort(): Pair<String?, String?> = when(val lastMessage = messages.lastOrNull()){
       //128 символов
        null -> "Сообщений нет" to "@John_Doe"
        is ImageMessage -> "${lastMessage.from.firstName} - отправил фото" to lastMessage.from.firstName
        else -> (lastMessage as TextMessage).text!!.truncate(128) to lastMessage.from.firstName
    }*/

    private fun isSingle(): Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }
}

enum class ChatType{
    SINGLE,
    GROUP,
    ARCHIVE
}



