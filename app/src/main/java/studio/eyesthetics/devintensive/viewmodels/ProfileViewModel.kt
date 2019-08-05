package ru.skillbranch.devintensive.viewmodels

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository

/**
 * Created by BashkatovSM on 23.07.2019
 */
 
class ProfileViewModel : ViewModel() {
    private val repository : PreferencesRepository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val initialsDrawable = MutableLiveData<Drawable>()

    init {
        profileData.value =repository.getProfile()
        appTheme.value = repository.getAppTheme()
    }

    fun getProfileData(): LiveData<Profile> = profileData

    fun getTheme():LiveData<Int> = appTheme

    fun getTextInitials(): LiveData<Drawable> = initialsDrawable

    fun saveProfileData(profile: Profile) {
        repository.saveProfile(profile)
        profileData.value = profile
    }

    fun switchTheme() {
        if(appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun updateTextInitials(colorId: Int) {
        val initials = repository.getInitials()
        if (initials.first.isNotEmpty() || initials.second.isNotEmpty()) {
            initialsDrawable.value = repository.getTextInitials(initials, colorId)
        }
    }
}