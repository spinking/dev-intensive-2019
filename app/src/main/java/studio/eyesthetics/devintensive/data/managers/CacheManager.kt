package ru.skillbranch.devintensive.data.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.DataGenerator

/**
 * Created by BashkatovSM on 26.08.2019
 */
object CacheManager {
    private val chats = mutableLiveData(DataGenerator.stabChats)
    private val users = mutableLiveData(DataGenerator.stabUsers)

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }

    fun findUsersByIds(ids: List<String>): List<User> {
        return users.value!!.filter { ids.contains(it.id) }
    }

    fun nextChatId(): String {
        //return "${chats.value!!.size}"
        val lastId = chats.value!!.last().id.toInt() + 1
        return lastId.toString()
    }

    fun insertChat(chat : Chat) {
        val copy = chats.value!!.toMutableList()
        copy.add(chat)
        chats.value = copy
    }
}