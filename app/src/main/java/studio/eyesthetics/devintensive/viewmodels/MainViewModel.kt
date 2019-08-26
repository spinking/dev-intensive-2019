package studio.eyesthetics.devintensive.viewmodels

import androidx.lifecycle.ViewModel
import studio.eyesthetics.devintensive.repositories.ChatRepository

/**
 * Created by BashkatovSM on 26.08.2019
 */
class MainViewModel: ViewModel() {
    private val chatRepository =  ChatRepository
}