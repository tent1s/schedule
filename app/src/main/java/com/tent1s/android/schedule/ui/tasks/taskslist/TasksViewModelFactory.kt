package com.tent1s.android.schedule.ui.tasks.taskslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.database.TasksDatabaseDao
import com.tent1s.android.schedule.database.TasksList
import com.tent1s.android.schedule.repository.ScheduleRepository


//class TasksViewModelFactory(private val tasks: LiveData<List<TasksList>>) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
//            return TasksViewModel(tasks) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}