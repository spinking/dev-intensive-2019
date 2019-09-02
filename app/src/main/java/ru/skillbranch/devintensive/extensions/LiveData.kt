package ru.skillbranch.devintensive.extensions

import androidx.lifecycle.MutableLiveData

/**
 * Created by BashkatovSM on 26.08.2019
 */

 fun <T>mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()

    if(defaultValue != null) {
        data.value = defaultValue
    }

    return data
 }