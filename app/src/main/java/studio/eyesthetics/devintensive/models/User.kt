package studio.eyesthetics.devintensive.models

import studio.eyesthetics.devintensive.utils.Utils
import java.util.*

/**
 * Created by Spinking on 27.06.2019
 */

data class User (
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    val lastVisit : Date? = Date(),
    var isOnline : Boolean = false

    )
{
    constructor(id:String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null)

    constructor(id:String) : this(id, "John", "Doe")

    init {
        println("It's Alive!!!\n" +
                "${if (lastName === "Doe") "His name is $firstName $lastName" else "And his name is $firstName $lastName!!!" }\n")
    }

    companion object Factory{
        private var lastId : Int = -1
        fun makeUser(fullName:String?) : User{
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }
}