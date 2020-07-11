package com.example.surrogatemessagereceiver

import android.app.Application
import com.example.surrogatemessagereceiver.chat.ChatModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        setupTimber()
    }


    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(ChatModule.create()))
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}