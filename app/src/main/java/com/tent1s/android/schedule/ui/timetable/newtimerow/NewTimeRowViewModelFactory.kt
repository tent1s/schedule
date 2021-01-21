package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tent1s.android.schedule.database.TimetableDatabaseDao



class NewTimeRowViewModelFactory(private val dataSource: TimetableDatabaseDao,
                              private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTimeRowViewModel::class.java)) {
            return NewTimeRowViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}