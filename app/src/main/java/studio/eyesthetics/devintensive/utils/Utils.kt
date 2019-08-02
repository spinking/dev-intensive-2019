package ru.skillbranch.devintensive.utils

/**
 * Created by Spinking on 27.06.2019.
 */

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val trim = fullName?.trim()

        val parts: List<String>? = trim?.split(" ")

        var firstName = parts?.getOrNull(0)

        var lastName = parts?.getOrNull(1)

        if (firstName.isNullOrBlank()) firstName = null
        if (lastName.isNullOrBlank()) lastName = null
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val fullName: List<String> = payload.split(" ").filter { a -> a.isNotBlank() }
        var res = ""
        for (word: String in fullName) {
            var sb = ""
            val charArray = word.toCharArray()
            for (ch: Char in charArray.iterator()) {
                val char = when (ch) {
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

                    'А' -> "A"
                    'Б' -> "B"
                    'В' -> "V"
                    'Г' -> "G"
                    'Д' -> "D"
                    'Е' -> "E"
                    'Ё' -> "E"
                    'Ж' -> "Zh"
                    'З' -> "Z"
                    'И' -> "I"
                    'Й' -> "I"
                    'К' -> "K"
                    'Л' -> "L"
                    'М' -> "M"
                    'Н' -> "N"
                    'О' -> "O"
                    'П' -> "P"
                    'Р' -> "R"
                    'С' -> "S"
                    'Т' -> "T"
                    'У' -> "U"
                    'Ф' -> "F"
                    'Х' -> "H"
                    'Ц' -> "C"
                    'Ч' -> "Ch"
                    'Ш' -> "Sh"
                    'Щ' -> "Sh'"
                    'Ъ' -> ""
                    'Ы' -> "I"
                    'Ь' -> ""
                    'Э' -> "E"
                    'Ю' -> "Yu"
                    'Я' -> "Ya"
                    else -> ch.toString()
                }
                sb = "$sb$char"
            }
            res = when {
                res.isNotBlank() -> "$res$divider$sb"
                else -> "$res$sb"
            }
            /*if (res.isNotEmpty() || (res != " ")) res = "$res$divider$sb"
            else res = "$res$sb"*/
        }

        return res
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName == null && lastName == null) return null
        val first = "${firstName?.firstOrNull() ?: ""}${lastName?.firstOrNull() ?: ""}"
        val p = Regex("\\s")
        if (first.matches(p)) return null
        return first.toUpperCase()
    }
}
