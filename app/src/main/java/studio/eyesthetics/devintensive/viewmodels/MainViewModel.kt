package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.*
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository
/**
 * Created by BashkatovSM on 26.08.2019
 */
class MainViewModel: ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository =  ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()){chats ->
        return@map chats.filter { !it.isArchived }
            .map { it.toChatItem() }
            .sortedBy { it.id.toInt() }
    }

    fun getChatData() : LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()
        var searchChats: MutableList<ChatItem>
        val filterF = {
            val queryStr = query.value!!
            if(chatRepository.getArchiveChatsCount() > 0) {
                searchChats = mutableListOf(
                    getArchiveItem()
                )
            } else {
                searchChats = mutableListOf()
            }

            searchChats.addAll(chats.value!!)

            result.value = if(queryStr.isEmpty()) searchChats
            else searchChats.filter { it.title.contains(queryStr, true) }
        }
        result.addSource(chats) { filterF.invoke() }
        result.addSource(query) {filterF.invoke()}
        return result
    }

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }

    private fun getArchiveItem(): ChatItem {
        return ChatItem(
            "none",
            "",
            "",
            "Архив чатов",
            chatRepository.getShortDescription(),
            chatRepository.getMessageCount(),
            chatRepository.getLastDate(),
            false,
            ChatType.ARCHIVE,
            chatRepository.getLastAuthor()
        )
    }
}