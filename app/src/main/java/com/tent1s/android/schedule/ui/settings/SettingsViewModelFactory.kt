package com.tent1s.android.schedule.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.database.TasksDatabaseDao
import com.tent1s.android.schedule.database.TimetableDatabaseDao
import com.tent1s.android.schedule.ui.timetable.newtimerow.NewTimeRowViewModel


class SettingsViewModelFactory(private val dataSourceTimetable: TimetableDatabaseDao, private val dataSourceTasks: TasksDatabaseDao,
                                 private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataSourceTimetable, dataSourceTasks, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}