package com.example.aop_part5_chpater01

import android.app.Application
import com.example.aop_part5_chpater01.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AopPart5Chapter01Application: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@AopPart5Chapter01Application)
            modules(appModule)
        }
        // TODO KOIN Trigger
    }
}