package com.tent1s.android.schedule.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.tent1s.android.schedule.database.TasksDatabaseDao
import kotlinx.coroutines.launch

class SettingsViewModel(private val firebase: DatabaseReference, private val databaseTasks: TasksDatabaseDao,
                        application: Application) : AndroidViewModel(application) {

    fun clearTimetable() {
        viewModelScope.launch {
            firebase.removeValue()
        }
    }

    fun clearTasks() {
        viewModelScope.launch {
            databaseTasks.clear()
        }
    }

}