package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository
/**
 * Created by BashkatovSM on 26.08.2019
 */
class MainViewModel: ViewModel() {
    fun getChatData() : LiveData<List<ChatItem>> {
        return mutableLiveData()
    }

    private val chatRepository =  ChatRepository
}