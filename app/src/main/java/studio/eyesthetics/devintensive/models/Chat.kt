package studio.eyesthetics.devintensive.models

/**
 * Created by BashkatovSM on 28.06.2019
 */
class Chat(
    val id:String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {
}