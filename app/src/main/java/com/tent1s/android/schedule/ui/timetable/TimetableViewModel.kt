package com.tent1s.android.schedule.ui.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimetableViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Нажмите “+”, чтобы добавить"
    }
    val text: LiveData<String> = _text
}