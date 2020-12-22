package com.tent1s.android.schedule

import android.app.Application
import timber.log.Timber

class LoggingByTimber  : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}