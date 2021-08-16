package com.bekzad.cryptotracker

import android.app.Application
import com.bekzad.cryptotracker.data.repository.CoinsRepository
import timber.log.Timber

class CryptoApplication : Application() {

    val coinsRepository: CoinsRepository
        get() = ServiceLocator.provideCoinsRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}