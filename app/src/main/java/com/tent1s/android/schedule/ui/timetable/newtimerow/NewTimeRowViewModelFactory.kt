package com.tent1s.android.schedule.ui.timetable.newtimerow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.tent1s.android.schedule.database.TimetableDatabaseDao



class NewTimeRowViewModelFactory(private val application: Application, private val timetableId : String,
                                 private val weekId : Int, private val firebase: DatabaseReference) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTimeRowViewModel::class.java)) {
            return NewTimeRowViewModel( application, timetableId, weekId, firebase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}