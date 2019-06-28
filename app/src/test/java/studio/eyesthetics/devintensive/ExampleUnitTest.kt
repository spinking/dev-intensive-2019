package studio.eyesthetics.devintensive

import org.junit.Test

import org.junit.Assert.*
import studio.eyesthetics.devintensive.extensions.TimeUnits
import studio.eyesthetics.devintensive.extensions.add
import studio.eyesthetics.devintensive.extensions.format
import studio.eyesthetics.devintensive.extensions.toUserView
import studio.eyesthetics.devintensive.models.*
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
        val user = User.makeUser("")
        val user2 = User.makeUser(" ")
        val user3 = User.makeUser(null)
        val user4 = User.makeUser("null")
        val user5 = User.makeUser("Spin")
        val user6 = User.makeUser("Spin ")
        val user7 = User.makeUser("King")
        val user8 = User.makeUser(" King")
        val user9 = User.makeUser("     King")
        val user10 = User.makeUser("Spin King")

        println("User Name \"\" = ${user.firstName} ${user.lastName}")
        println("User Name \" \" = ${user2.firstName} ${user2.lastName}")
        println("User Name ${null} = ${user3.firstName} ${user3.lastName}")
        println("User Name \"null\" = ${user4.firstName} ${user4.lastName}")
        println("User Name \"Spin\" = ${user5.firstName} ${user5.lastName}")
        println("User Name \"Spin \" = ${user6.firstName} ${user6.lastName}")
        println("User Name \"King\" = ${user7.firstName} ${user7.lastName}")
        println("User Name \" King\" = ${user8.firstName} ${user8.lastName}")
        println("User Name \"     King\" = ${user9.firstName} ${user9.lastName}")
        println("User Name \"Spin King\" = ${user10.firstName} ${user10.lastName}")
    }
}
