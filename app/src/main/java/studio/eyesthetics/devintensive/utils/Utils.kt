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
        val fullName : List<String> = payload.toLowerCase().split(" ")
        var res = ""
        for(word: String in fullName) {
            var sb = ""
            val charArray = word.toCharArray()
            for(ch:Char in charArray.iterator()) {
                val char = when(ch) {
                    'а' -> "a"
                    'б' -> "b"
                    'в' -> "v"
                    'г' -> "g"
                    'д' -> "d"
                    'е' -> "e"
                    'ё' -> "e"
                    'ж' -> "zh"
                    'з' -> "z"
                    'и' -> "i"
                    'й' -> "i"
                    'к' -> "k"
                    'л' -> "l"
                    'м' -> "m"
                    'н' -> "n"
                    'о' -> "o"
                    'п' -> "p"
                    'р' -> "r"
                    'с' -> "s"
                    'т' -> "t"
                    'у' -> "u"
                    'ф' -> "f"
                    'х' -> "h"
                    'ц' -> "c"
                    'ч' -> "ch"
                    'ш' -> "sh"
                    'щ' -> "sh'"
                    'ъ' -> ""
                    'ы' -> "i"
                    'ь' -> ""
                    'э' -> "e"
                    'ю' -> "yu"
                    'я' -> "ya"
                    else -> ch.toString()
                }
                sb = "$sb$char".capitalize()
            }
            if(res.isNotEmpty()) res = "$res$divider$sb"
            else res = "$res$sb"
        }

        return "return $res"
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if(firstName == null && lastName == null) return null
        val first = "${firstName?.firstOrNull()?:""}${lastName?.firstOrNull()?:""}"
        val p = Regex("\\s")
        if(first.matches(p)) return null
        return first.toUpperCase()
    }




}