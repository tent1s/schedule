package com.tent1s.android.schedule.ui.tasks.newtask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.database.TasksDatabaseDao
import com.tent1s.android.schedule.ui.tasks.taskslist.TasksViewModel


class NewTaskViewModelFactory(private val dataSource: TasksDatabaseDao,
                            private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)) {
            return NewTaskViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}