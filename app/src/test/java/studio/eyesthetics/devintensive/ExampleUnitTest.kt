package studio.eyesthetics.devintensive

import org.junit.Test

import org.junit.Assert.*
import studio.eyesthetics.devintensive.extensions.*
import studio.eyesthetics.devintensive.models.*
import studio.eyesthetics.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
    }

    @Test
    fun test_factory() {
        //val user = User.makeUser("John Cena")
        //val user2 = User.makeUser("John Wick")
        val user = User.makeUser("John Wick")
        val user2 = user.copy(id = "2", lastName = "Cena")
        print("$user \n$user2")
    }
    @Test
    fun test_decomposition() {
        val user = User.makeUser("John Wick")

        fun getUserInfo() = user

        val(id, firstName, lastName) = getUserInfo()

        println("$id, $firstName, $lastName")
        println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    @Test
    fun test_copy() {
        val user = User.makeUser("John Wick")
        var user2 = user.copy(lastVisit = Date())
        var user3 = user.copy(lastVisit = Date().add(-2, TimeUnits.SECOND))
        var user4 = user.copy(lastName = "Cena", lastVisit = Date().add(2, TimeUnits.HOUR))

        println("""
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent())
    }
    @Test
    fun test_data_mapping() {
        val user = User.makeUser("Spin King")
        val newUser = user.copy(lastVisit = Date().add(-800, TimeUnits.DAY))
        println(newUser)

        val userView = newUser.toUserView()

        userView.printMe()

    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Spin King")
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any text message", type = "text")
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image")

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_parse_full_name() {
        println(Utils.parseFullName(null))
        println(Utils.parseFullName(""))
        println(Utils.parseFullName(" "))
        println(Utils.parseFullName("John"))

        /*Реализуй метод Utils.parseFullName(fullName) принимающий в качестве аргумента полное имя пользователя (null, пустую строку) и возвращающий пару значений Pair(firstName, lastName) при невозможности распарсить полное имя или его часть вернуть null null/"firstName" null
        Пример:
        Utils.parseFullName(null) //null null
        Utils.parseFullName("") //null null
        Utils.parseFullName(" ") //null null
        Utils.parseFullName("John") //John null*/
    }

    @Test
    fun test_date_format() {
        println(Date().format())
        println(Date().format("HH:mm"))

        /*Реализуй extension Date.format(pattern) возвращающий отформатированную дату по паттерну передаваемому в качестве аргумента (значение по умолчанию "HH:mm:ss dd.MM.yy" локаль "ru")
        Пример:
        Date().format() //14:00:00 27.06.19
        Date().format("HH:mm") //14:00*/
    }

    @Test
    fun test_date_add() {
        println(Date().add(2, TimeUnits.SECOND))
        println(Date().add(-4, TimeUnits.DAY))

        /*Реализуй extension Date.add(value, TimeUnits) добавляющий или вычитающий значение переданное первым аргументом в единицах измерения второго аргумента (enum TimeUnits [SECOND, MINUTE, HOUR, DAY]) и возвращающий модифицированный экземпляр Date
                Пример:
        Date().add(2, TimeUnits.SECOND) //Thu Jun 27 14:00:02 GST 2019
        Date().add(-4, TimeUnits.DAY) //Thu Jun 23 14:00:00 GST 2019*/
    }

    @Test
    fun test_to_initials() {
        assertEquals(Utils.toInitials("John", "doe"), "JD")
        assertEquals(Utils.toInitials("John", null), "J")
        assertEquals(Utils.toInitials(null, null), null)
        assertEquals(Utils.toInitials(" ", ""), null)

       /* Реализуй метод Utils.toInitials(firstName lastName) принимающий в качестве аргументов имя и фамилию пользователя (null, пустую строку) и возвращающий строку с первыми буквами имени и фамилии в верхнем регистре (если один из аргументов null то вернуть один инициал, если оба аргумента null вернуть null)
        Пример:
        Utils.toInitials("john" ,"doe") //JD
        Utils.toInitials("John", null) //J
        Utils.toInitials(null, null) //null
        Utils.toInitials(" ", "") //null*/
    }



    @Test
    fun test_to_transliteration() {
        assertEquals(Utils.transliteration("Иван Стереотипов"), "Ivan Stereotipov")
        assertEquals(Utils.transliteration("Amazing Петр","_"), "Amazing_Petr")
        assertEquals(Utils.transliteration("оЖЖо","_"), "oZhZho")

        /*Реализуй метод Utils.transliteration(payload divider) принимающий в качестве аргумента строку (divider по умолчанию " ") и возвращающий преобразованную строку из латинских символов, словарь символов соответствия алфовитов доступен в ресурсах к заданию
        Пример:
        Utils.transliteration("Иван Стереотипов") //Ivan Stereotipov
        Utils.transliteration("Amazing Петр","_") //Amazing_Petr*/
    }

    @Test
    fun test_to_humanizeDiff() {
        println(Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        println(Date().add(-5, TimeUnits.DAY).humanizeDiff())
        println(Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        println(Date().add(7, TimeUnits.DAY).humanizeDiff())
        println(Date().add(-400, TimeUnits.DAY).humanizeDiff())
        println(Date().add(400, TimeUnits.MINUTE).humanizeDiff())

        /*Реализуй extension Date.humanizeDiff(date) (значение по умолчанию текущий момент времени) для форматирования вывода разницы между датами в человекообразном формате, с учетом склонения числительных. Временные интервалы преобразований к человекообразному формату доступны в ресурсах к заданию
        Пример:
        Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
        Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
        Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
        Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
        Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
        Date().add(400, TimeUnits.MINUTE).humanizeDiff() //более чем через год*/
    }
}
