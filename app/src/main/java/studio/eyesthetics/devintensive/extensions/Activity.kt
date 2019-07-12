package studio.eyesthetics.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by BashkatovSM on 12.07.2019
 */

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
/*
fun Activity.isKeyboardOpen() {
    val rootView: View = this.window.decorView
    val metrics: DisplayMetrics = this.resources.displayMetrics
    val height = metrics.heightPixels
    val rootHeight = rootView.getWindowVisibleDisplayFrame(Rect())
    Log.d("M_Activity", "isKeyboardOpen")
}*/


