package com.bekzad.cryptotracker

import android.app.Application
import timber.log.Timber

class CryptoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}