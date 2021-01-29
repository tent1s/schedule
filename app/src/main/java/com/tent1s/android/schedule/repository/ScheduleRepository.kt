package com.tent1s.android.schedule.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tent1s.android.schedule.database.*


class ScheduleRepository(application: Application)  {

    private val dataSourceForTasksList = ScheduleDatabase.getInstance(application).tasksDatabaseDao
    val tasks = dataSourceForTasksList.getAllTasks()

    private val dataSourceForTimetableList = ScheduleDatabase.getInstance(application).timetableDatabaseDao
    val timetable = dataSourceForTimetableList.getAllTimetable()

}


