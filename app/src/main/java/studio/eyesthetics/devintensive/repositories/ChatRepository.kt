package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.Chat
import ru.skillbranch.devintensive.models.data.ChatItem

/**
 * Created by BashkatovSM on 26.08.2019
 */
object ChatRepository {
    private val chats = CacheManager.loadChats()

    fun loadChats() : MutableLiveData<List<Chat>> {
    //add object DataGenerator in utils (time: 1: 04, 1:07 tutorial 5)
        return chats
    }

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val find = chats.value!!.indexOfFirst{ it.id == chat.id }
        if(find == -1) return
        copy[find] = chat
        chats.value = copy

    }

    fun find(chatId: String): Chat? {
        val find = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(find)
    }
}