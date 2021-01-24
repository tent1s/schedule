package com.tent1s.android.schedule

import android.app.Application
import android.content.Context
import com.tent1s.android.schedule.repository.ScheduleRepository
import timber.log.Timber

class ScheduleApplication : Application() {
    lateinit var application: Context

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
         application = applicationContext
    }

    private val _repository by lazy { ScheduleRepository(application as Application) }
    val repository
        get() = _repository

}