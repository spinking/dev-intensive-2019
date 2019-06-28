package studio.eyesthetics.devintensive.utils

/**
 * Created by Spinking on 27.06.2019.
 */

object Utils {
    fun parseFullName(fullName:String?) : Pair<String?, String?> {

        val parts : List<String>? = fullName?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if(firstName == "") firstName = null
        if(lastName == "") lastName = null
        //return Pair(firstName, lastName)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        TODO("not implemented")

    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        //val firstParts : List<String>? = firstName?.split("")
        //val lastParts : List<String>? = firstName?.split("")
        var firstNameInitial = firstName?.get(0)
        var lastNameInitial = lastName?.get(0)
        return "$firstNameInitial $lastNameInitial"
    }


}