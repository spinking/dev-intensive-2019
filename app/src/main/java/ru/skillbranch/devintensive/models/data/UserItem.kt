package ru.skillbranch.devintensive.models.data

/**
 * Created by BashkatovSM on 26.08.2019
 */
data class UserItem (
    val id: String,
    val fullName: String,
    val initials: String?,
    val avatar: String?,
    var lastActivity: String,
    var isSelected: Boolean = false,
    var isOnline: Boolean = false
)