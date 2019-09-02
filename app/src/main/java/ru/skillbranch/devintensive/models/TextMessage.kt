package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*
import kotlin.math.min

class TextMessage(
    id: String,
    from: User,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    isReaded:Boolean = false,
    var text: String?
) : BaseMessage(id, from, chat, isIncoming, date, isReaded) {

    override fun shortMessage(): String =
        with(text) {
            if (this.isNullOrEmpty()) ""
            else this.substring(0, min(this.length, 128))
        }


    override fun formatMessage(): String =
        "id:$id ${from.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
}