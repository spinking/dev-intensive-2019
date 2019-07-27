package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.validUrl
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel


class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
        const val IS_VALID_REPO = "IS_VALID_REPO"
    }

    private lateinit var viewModel: ProfileViewModel
    var isEditMode = false
    var isValidRepo = true
    lateinit var viewFields : Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
        Log.d("M_ProfileActivity", "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
        outState?.putBoolean(IS_VALID_REPO, isValidRepo)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer{updateUI(it)})
        viewModel.getTheme().observe(this, Observer{updateTheme(it)})
        viewModel.getTextInitials().observe(this, Observer { iv_avatar.setImageDrawable(it) })
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity", "updateTheme")
        delegate.setLocalNightMode(mode)
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields) {
                v.text = it[k].toString()
            }
            btn_switch_theme.setOnClickListener{
                viewModel.switchTheme()
                viewModel.updateTextInitials()
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        isValidRepo = savedInstanceState?.getBoolean(IS_VALID_REPO, false) ?: false
        showCurrentMode(isEditMode)

        et_repository.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().validUrl()) {
                    isValidRepo = true
                    wr_repository.error = null
                    wr_repository.isErrorEnabled = false
                } else {
                    isValidRepo = false
                    wr_repository.error = "Невалидный адрес репозитория"
                }
            }
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })


        btn_edit.setOnClickListener {
            if(isValidRepo) {
                if(isEditMode) saveProfileInfo()
                isEditMode = !isEditMode
                showCurrentMode(isEditMode)
                viewModel.updateTextInitials()
            }
         }

    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("firstName", "lastName", "about", "repository").contains(it.key) }
        for((_, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if(isEdit) 255 else 0
        }

        ic_eye.visibility = if(isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? = if(isEdit) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){

                    when(viewModel.getTheme().value) {
                        2 -> PorterDuffColorFilter(resources.getColor(R.color.color_accent_night, theme), PorterDuff.Mode.SRC_IN)
                        else -> PorterDuffColorFilter(resources.getColor(R.color.color_accent, theme), PorterDuff.Mode.SRC_IN)
                    }

                } else{

                    when(viewModel.getTheme().value) {
                        2 -> PorterDuffColorFilter(resources.getColor(R.color.color_accent_night), PorterDuff.Mode.SRC_IN)
                        else -> PorterDuffColorFilter(resources.getColor(R.color.color_accent), PorterDuff.Mode.SRC_IN)
                    }
                }
            } else {
                null
            }

            val icon = if(isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }
}
