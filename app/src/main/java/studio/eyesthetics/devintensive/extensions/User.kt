package studio.eyesthetics.devintensive.extensions

import studio.eyesthetics.devintensive.models.User
import studio.eyesthetics.devintensive.models.UserView
import studio.eyesthetics.devintensive.utils.Utils
import java.util.*

/**
 * Created by BashkatovSM on 28.06.2019
 */

fun User.toUserView() : UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = if(lastVisit == null) "Еще ни разу не был" else if (isOnline) "online" else "Последний раз был ${lastVisit.humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        initials = initials,
        avatar = avatar,
        status = status )
}



