package com.tent1s.android.schedule.ui.tasks.taskslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.repository.ScheduleRepository



class TasksViewModelFactory(private val repository: ScheduleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}