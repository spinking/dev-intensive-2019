package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.dpToPx (dp: Float): Float {
    return TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP , dp, this.resources.displayMetrics)
}