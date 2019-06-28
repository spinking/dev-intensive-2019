package studio.eyesthetics.devintensive.models

import android.service.voice.AlwaysOnHotwordDetector
import java.util.*

/**
 * Created by BashkatovSM on 28.06.2019
 */
abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String
    companion object AbstractFactory {
        var lastId = -1
        fun makeMessage(from:User?, chat: Chat, date: Date = Date(), type: String = "text", payload: Any?): BaseMessage{
            lastId++
            return when(type) {
                "image" -> ImageMessage("$lastId", from, chat, date = date, image = payload as String)
                else -> TextMessage("$lastId", from, chat, date= date, text = payload as String)
            }
        }
    }
}