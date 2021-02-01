package com.tent1s.android.schedule.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.tent1s.android.schedule.database.TasksDatabaseDao


class SettingsViewModelFactory(private val firebase: DatabaseReference, private val dataSourceTasks: TasksDatabaseDao,
                               private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(firebase, dataSourceTasks, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}