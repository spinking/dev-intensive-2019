package ru.skillbranch.devintensive.data.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.Chat

/**
 * Created by BashkatovSM on 26.08.2019
 */
object CacheManager {
    val chats = mutableLiveData(DataGenerator.stabChats)

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }
}