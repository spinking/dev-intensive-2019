package studio.eyesthetics.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View


/**
 * Created by BashkatovSM on 12.07.2019
 */

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Activity.isKeyboardOpen(): Boolean{
    val rootView = findViewById<View>(android.R.id.content)
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val heightDiff = rootView.height - rect.height()
    val err = this.dpToPx(20F)
    Log.d("M_", "${heightDiff > err}")

    return heightDiff > err
}

fun Activity.isKeyboardClosed(): Boolean {
    return this.isKeyboardOpen().not()
}



