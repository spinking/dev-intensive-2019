package ru.skillbranch.devintensive

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen

import studio.eyesthetics.devintensive.models.Bender

class ProfileActivity : AppCompatActivity() {

/**
    * Вызывается при первом создании или перезапуске Activity
    *
    * Здесь задается внешний вид активности (UI) через метод setContentView().
    * инициализируются представления
    * представления связываются с необходимыми данными и ресурсами
    * связываются данными со списками
    *
    * Этот метод также предоставляет Bundle, содержащий ранее сохранённое
    * состояние Activity, если оно было.
    *
    * Всегда сопровождается вызовом onStart()
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }
}
