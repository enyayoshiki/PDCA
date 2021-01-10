package com.example.pdca.application

import android.app.Application
import com.example.pdca.roomdata.CycleDatabase
import timber.log.Timber

class PdcaApplication : Application() {

    companion object {
        lateinit var db: CycleDatabase
    }


    override fun onCreate() {
        super.onCreate()
        db = CycleDatabase.getDatabase(applicationContext)

        initialize()
    }

    private fun initialize() {
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}