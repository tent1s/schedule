package com.tent1s.android.schedule.ui.tasks.newtask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.database.TasksDatabaseDao


class NewTaskViewModelFactory(private val dataSource: TasksDatabaseDao,
                            private val application: Application, private val taskId: Long) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)) {
            return NewTaskViewModel(dataSource, application, taskId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}