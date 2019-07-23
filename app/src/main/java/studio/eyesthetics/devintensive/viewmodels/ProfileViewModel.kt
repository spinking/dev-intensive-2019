package studio.eyesthetics.devintensive.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.eyesthetics.devintensive.models.Profile
import studio.eyesthetics.devintensive.repositories.PreferencesRepository

/**
 * Created by BashkatovSM on 23.07.2019
 */
 
class ProfileViewModel : ViewModel() {
    private val repository : PreferencesRepository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()

    init {
        Log.d("M_ProfileViewModel", "init view model")
        profileData.value =repository.getProfile()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("M_ProfileViewModel", "view model cleared")
    }

    fun getProfileData(): LiveData<Profile> = profileData

    fun saveProfileData(profile: Profile) {
        repository.saveProfile(profile)
        profileData.value = profile
    }
}