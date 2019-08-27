package ru.skillbranch.devintensive.models


import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

/**
 * Created by BashkatovSM on 28.06.2019
 */
class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    var isReaded: Boolean = false,
    var text: String?
) : BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String = "${from?.firstName}" +
    "${if(isIncoming) " получил" else " отправил" } сообщение \"$text\" ${date.humanizeDiff()}"
}

