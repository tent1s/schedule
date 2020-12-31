package com.tent1s.android.schedule

import android.app.Application
import com.tent1s.android.schedule.repository.ScheduleRepository
import timber.log.Timber

class ScheduleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }


    private val _repository by lazy { ScheduleRepository() }
    val repository
        get() = _repository
}