package ru.skillbranch.devintensive

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
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
    fun test_builder() {
        val user = User.Builder("1").id("2")
            .firstName("John")
            .lastName("Wick")
            .avatar("avatar")
            .rating(1)
            .respect(2)
            .lastVisit(Date().add(-5, TimeUnits.MINUTE))
            .isOnline(false)
            .build()
        val user2 = User.Builder("3")
            .firstName("John")
            .respect(3)
            .isOnline(true)
            .build()
        val user3 = User.Builder("4")

        println("$user")
        println("$user2")
        println("$user3")
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
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any text message", type = "text", isIncoming = false)
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image", isIncoming = true)

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_parse_full_name() {
        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals("John" to null, Utils.parseFullName("John"))

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
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())

        /*Реализуй extension Date.humanizeDiff(date) (значение по умолчанию текущий момент времени) для форматирования вывода разницы между датами в человекообразном формате, с учетом склонения числительных. Временные интервалы преобразований к человекообразному формату доступны в ресурсах к заданию
        Пример:
        Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
        Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
        Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
        Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
        Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
        Date().add(400, TimeUnits.MINUTE).humanizeDiff() //более чем через год*/
    }

    @Test
    fun test_to_humanizeDiff_2() {
        val new = Date()

        assertEquals("только что", Date().add(-1, TimeUnits.SECOND).humanizeDiff(new))
        assertEquals("несколько секунд назад", Date().add(-45, TimeUnits.SECOND).humanizeDiff(new))
        assertEquals("минуту назад", Date().add(-46, TimeUnits.SECOND).humanizeDiff(Date()))
        assertEquals("1 минуту назад", Date().add(-76, TimeUnits.SECOND).humanizeDiff(Date()))
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("45 минут назад", Date().add(-45, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("1 час назад", Date().add(-76, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("2 часа назад", Date().add(-120, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("день назад", Date().add(-26, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("1 день назад", Date().add(-27, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("360 дней назад", Date().add(-360, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("более года назад", Date().add(-361, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("20 часов назад", Date().add(-20, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("21 час назад", Date().add(-21, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("111 дней назад", Date().add(-111, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("1 час назад", Date().add(-111, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("15 минут назад", Date().add(-15, TimeUnits.MINUTE).humanizeDiff(Date()))

        assertEquals("через несколько секунд", Date().add(1, TimeUnits.SECOND).humanizeDiff(Date()))
        assertEquals("через несколько секунд", Date().add(2, TimeUnits.SECOND).humanizeDiff(Date()))
        assertEquals("через минуту", Date().add(1, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("через 3 минуты", Date().add(3, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("через 5 минут", Date().add(5, TimeUnits.MINUTE).humanizeDiff(Date()))
        assertEquals("через час", Date().add(1, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("через 2 часа", Date().add(2, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("через 3 часа", Date().add(3, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("через 4 часа", Date().add(4, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("через 5 часов", Date().add(5, TimeUnits.HOUR).humanizeDiff(Date()))
        assertEquals("через день", Date().add(1, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("через 4 дня", Date().add(4, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("через 5 дней", Date().add(5, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("через 148 дней", Date().add(148, TimeUnits.DAY).humanizeDiff(Date()))
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff(Date()))
    }

    @Test
    fun humanizeDiffTest() {
        var messageDate = Date()
        var currDate = Date()

        fun resetDates(messDate: Date, currentDate: Date) {
            messageDate = Date()
            currDate = Date()
        }

        resetDates(messageDate, currDate)

        Assert.assertEquals("2 часа назад", messageDate.add(-2, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("52 дня назад", messageDate.add(-52, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("через 2 минуты", messageDate.add(2, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("через 7 дней", messageDate.add(7, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("более года назад", messageDate.add(-400, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("более чем через год", messageDate.add(400, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)

        Assert.assertEquals("только что", messageDate.add(-1, TimeUnits.SECOND).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("несколько секунд назад", messageDate.add(-45, TimeUnits.SECOND).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("минуту назад", messageDate.add(-46, TimeUnits.SECOND).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("1 минуту назад", messageDate.add(-76, TimeUnits.SECOND).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("минуту назад", messageDate.add(-1, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("2 минуты назад", messageDate.add(-2, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("3 минуты назад", messageDate.add(-3, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("45 минут назад", messageDate.add(-45, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("час назад", messageDate.add(-1, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("1 час назад", messageDate.add(-76, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("2 часа назад", messageDate.add(-120, TimeUnits.MINUTE).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("3 часа назад", messageDate.add(-3, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("4 часа назад", messageDate.add(-4, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("5 часов назад", messageDate.add(-5, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)

        Assert.assertEquals("день назад", messageDate.add(-26, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("1 день назад", messageDate.add(-27, TimeUnits.HOUR).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("4 дня назад", messageDate.add(-4, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("5 дней назад", messageDate.add(-5, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("360 дней назад", messageDate.add(-360, TimeUnits.DAY).humanizeDiff(currDate))

        resetDates(messageDate, currDate)
        Assert.assertEquals("более года назад", messageDate.add(-361, TimeUnits.DAY).humanizeDiff(currDate))
    }

    //=================================================================
    @Test
    fun test_of_BaseMessage_AbstractFactory(){

        val vasya = User("0", "Василий", null)

        val msg_1 = BaseMessage.makeMessage(vasya,
            Chat("0"),
            Date(),
            "text",
            "any text message")
        val msg_2 = BaseMessage.makeMessage(vasya,
            Chat("1"),
            Date().add(-2, TimeUnits.HOUR),
            "image",
            "https://anyurl.com",
            true)

        assertEquals("id:0 Василий отправил сообщение \"any text message\" только что", msg_1.formatMessage())
        assertEquals("id:1 Василий получил изображение \"https://anyurl.com\" 2 часа назад", msg_2.formatMessage())
    }


    @Test
    fun test_of_parseFullName(){
        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals("John" to null, Utils.parseFullName("John"))
        assertEquals(null to null, Utils.parseFullName("    "))
        assertEquals("John" to null, Utils.parseFullName("John "))
        assertEquals("John" to null, Utils.parseFullName("  John "))
    }


    @Test
    fun test_of_transliteration() {

        println(  Utils.transliteration("Чингиз Байшурин") )
        println(  Utils.transliteration("Натан Щарянский Самуилович", "_") )
        assertEquals( "Chingiz Baishurin", Utils.transliteration("Чингиз Байшурин") )
        assertEquals( "Ivan Stereotipov", Utils.transliteration("Иван Стереотипов") )
        assertEquals( "Amazing_Petr", Utils.transliteration("Amazing Петр", divider = "_") )

        assertEquals( "Zh Zh", Utils.transliteration("Ж Ж") )
        assertEquals( "ZhZh", Utils.transliteration("ЖЖ") )
        assertEquals( "AbrAKadabra", Utils.transliteration("AbrAKadabra") )
        assertEquals( "StraNNIi NikVash'e", Utils.transliteration("СтраННЫй НикВаще") )
    }


    @Test
    fun test_of_humanizeDiff() {
        // ----- Past -----
        assertEquals("только что", Date().add(-1, TimeUnits.SECOND).humanizeDiff())
        assertEquals("несколько секунд назад", Date().add(-45, TimeUnits.SECOND).humanizeDiff())
        assertEquals("минуту назад", Date().add(-46, TimeUnits.SECOND).humanizeDiff())
        assertEquals("1 минуту назад", Date().add(-76, TimeUnits.SECOND).humanizeDiff())
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("45 минут назад", Date().add(-45, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("1 час назад", Date().add(-76, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-120, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("день назад", Date().add(-26, TimeUnits.HOUR).humanizeDiff())
        assertEquals("1 день назад", Date().add(-27, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("360 дней назад", Date().add(-360, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-361, TimeUnits.DAY).humanizeDiff())

        // ----- Future ------
        assertEquals("через несколько секунд", Date().add(2, TimeUnits.SECOND).humanizeDiff())
        assertEquals("через минуту", Date().add(1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 3 минуты", Date().add(3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 5 минут", Date().add(5, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через час", Date().add(1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 2 часа", Date().add(2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 3 часа", Date().add(3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 4 часа", Date().add(4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 5 часов", Date().add(5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через день", Date().add(1, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 4 дня", Date().add(4, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 5 дней", Date().add(5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 148 дней", Date().add(148, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
    }


    @Test
    fun test_of_toInitials() {
        assertEquals("JD",  Utils.toInitials("john" ,"doe") )
        assertEquals("J",  Utils.toInitials("John", null) )
        assertEquals("D",  Utils.toInitials(null, "doe") )
        assertEquals(null,  Utils.toInitials(null, null) )
        assertEquals(null,  Utils.toInitials(" ", "") )
    }

    @Test
    fun test_messages() {
        val user = User.makeUser("Максим Никельман")
        val chat = Chat("1")
        val date = Date()
        assertEquals("Максим отправил сообщение \"any text message\" только что", BaseMessage.makeMessage(user, chat, date, "text", "any text message").formatMessage())
        assertEquals("Максим получил изображение \"https://anyurl.com\" 2 часа назад", BaseMessage.makeMessage(user, chat, date.add(-2, TimeUnits.HOUR), "image", "https://anyurl.com",true).formatMessage())
    }
    //=================================================================

    @Test
    fun test_id() {
        val user = User.Builder().build()
        println(user.id)
    }
}
