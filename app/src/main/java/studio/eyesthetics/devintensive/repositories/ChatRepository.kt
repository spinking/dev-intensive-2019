package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.models.Chat

/**
 * Created by BashkatovSM on 26.08.2019
 */
object ChatRepository {
    fun loadChats() : List<Chat> {
    //add object DataGenerator in utils (time: 1: 04, 1:07 tutorial 5)
        return DataGenerator.generateChats(10)
    }
}