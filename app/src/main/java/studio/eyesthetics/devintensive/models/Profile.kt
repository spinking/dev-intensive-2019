package ru.skillbranch.devintensive.models

/**
 * Created by BashkatovSM on 23.07.2019
 */
data class Profile(
    val firstName: String,
    val lastName: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {
    val nickName: String = "John Doe" //TODO implement me
    val rank: String = "Junior Android Developer"
    fun toMap() : Map<String, Any> = mapOf(
        "nickName" to nickName,
        "rank" to rank,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )

}