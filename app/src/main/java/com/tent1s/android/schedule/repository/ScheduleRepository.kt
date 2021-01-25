package com.tent1s.android.schedule.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tent1s.android.schedule.database.*


class ScheduleRepository(application: Application)  {

    private val dataSourceForTasksList = ScheduleDatabase.getInstance(application).tasksDatabaseDao
    val tasks = dataSourceForTasksList.getAllTasks()

    private val dataSourceForTimetableList = ScheduleDatabase.getInstance(application).timetableDatabaseDao
    val timetable = dataSourceForTimetableList.getAllTimetable()

    private var _weekId : Int = 0
    val weekId
        get() = _weekId

    fun setWeek(week: Int){
        _weekId = week
    }

    private var _theme : Boolean = false
    val theme
        get() = _theme

    fun setTheme(theme: Boolean){
        _theme = theme
    }

}


