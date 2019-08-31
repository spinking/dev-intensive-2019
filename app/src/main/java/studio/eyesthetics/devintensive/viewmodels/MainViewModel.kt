package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.*
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel: ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val chats = chatRepository.loadChats()

    fun getChatData() : LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!

            val (archivedChats, unarchivedChats) = this.chats.value!!.partition { it.isArchived }

            val chatItems = (
                    if (queryStr.isEmpty()) unarchivedChats.map { it.toChatItem() }
                    else unarchivedChats.map { it.toChatItem() }
                        .filter { it.title.contains(queryStr, true) })
                .sortedBy { it.id.toInt() }
                .toMutableList()


            if (archivedChats.isEmpty().not()) {
                val lastChat = archivedChats.sortedByDescending { it.lastMessageDate() }.first()
                chatItems.add(0, ChatItem(
                    "none",
                    "",
                    "",
                    "Архив чатов",
                    lastChat.lastMessageShort().first,
                    archivedChats.sumBy { it.unreadableMessageCount() },
                    lastChat.lastMessageDate()?.shortFormat(),
                    false,
                    ChatType.ARCHIVE,
                    lastChat.lastMessageShort().second
                ))
            }

            result.value = chatItems
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
}