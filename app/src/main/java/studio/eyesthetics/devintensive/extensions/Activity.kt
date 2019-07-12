package studio.eyesthetics.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager

/**
 * Created by BashkatovSM on 12.07.2019
 */

 val TAG = "Activty Extension"

 fun Activity.hideKeyboard() {
    val view = this.currentFocus
     val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

     if(isKeyboardOpen()) imm?.let { it.hideSoftInputFromWindow(view.windowToken, 0) }

     if(isKeyboardClosed()) imm?.let { it.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0) }

    /*val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.let {
            it.hideSoftInputFromWindow(v.windowToken, 0)
        }

    }*/
}

 fun Activity.isKeyboardOpen(): Boolean {
     val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
     Log.d("M_Activity", "${imm?.isActive} isKeyboardOpen")
     return if (imm != null) imm.isActive else false
 }

 fun Activity.isKeyboardClosed(): Boolean {
     val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
     Log.d("M_Activity", "${imm?.isActive} isKeyboardClosed")
     return return if (imm != null) !imm.isActive else false
 }

