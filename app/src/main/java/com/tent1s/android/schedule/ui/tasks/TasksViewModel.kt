package com.tent1s.android.schedule.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Нажмите “+”, чтобы добавить"
    }
    val text: LiveData<String> = _text
}