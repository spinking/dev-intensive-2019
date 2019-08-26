package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.models.User

/**
 * Created by BashkatovSM on 26.08.2019
 */
object GroupRepository {
    fun loadUsers(): List<User> = DataGenerator.stabUsers
}