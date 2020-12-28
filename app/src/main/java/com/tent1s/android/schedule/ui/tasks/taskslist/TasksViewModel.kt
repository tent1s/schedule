package com.tent1s.android.schedule.ui.tasks.taskslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tent1s.android.schedule.repository.ScheduleRepository

class TasksViewModel : ViewModel() {

    private val _navigateToSearch = MutableLiveData<Boolean>()
    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    fun onFabClicked(){
        _navigateToSearch.value = true
    }

    fun onNavigationToSearch(){
        _navigateToSearch.value = false
    }

    val tasks = ScheduleRepository.getTasks()

    private val _text = MutableLiveData<String>().apply {
        //value = "Нажмите “+”, чтобы добавить"
    }
    val text: LiveData<String> = _text
}