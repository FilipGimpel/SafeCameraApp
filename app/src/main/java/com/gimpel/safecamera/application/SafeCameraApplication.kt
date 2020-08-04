package com.gimpel.safecamera.application

import android.app.Application
import com.gimpel.safecamera.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class SafeCameraApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SafeCameraApplication)
            modules(listOf(appModule))
        }
    }
}