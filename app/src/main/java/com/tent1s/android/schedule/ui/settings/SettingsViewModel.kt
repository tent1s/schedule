package com.tent1s.android.schedule.ui.settings

import android.app.Application
import androidx.lifecycle.*
import com.tent1s.android.schedule.database.TasksDatabaseDao
import com.tent1s.android.schedule.database.TimetableDatabaseDao
import kotlinx.coroutines.launch

class SettingsViewModel(private val databaseTimetable: TimetableDatabaseDao, private val databaseTasks: TasksDatabaseDao,
                        application: Application) : AndroidViewModel(application) {

    fun clearTimetable() {
        viewModelScope.launch {

        }
    }
    fun clearTasks() {
        viewModelScope.launch {
            databaseTasks.clear()
        }
    }

}