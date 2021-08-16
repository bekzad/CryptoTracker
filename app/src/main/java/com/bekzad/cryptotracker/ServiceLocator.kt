package com.bekzad.cryptotracker

import android.content.Context
import androidx.room.Room
import com.bekzad.cryptotracker.data.repository.CoinsRepository
import com.bekzad.cryptotracker.data.repository.DefaultCoinsRepository
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import com.bekzad.cryptotracker.data.source.local.CoinsLocalDataSource
import com.bekzad.cryptotracker.data.source.remote.CoinsRemoteDataSource

object ServiceLocator {

    private var database: CoinDatabase? = null

    @Volatile
    var coinsRepository: CoinsRepository? = null

    fun provideCoinsRepository(context: Context): CoinsRepository {
        synchronized(this) {
            return coinsRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): CoinsRepository {
        val newRepo = DefaultCoinsRepository(CoinsRemoteDataSource, createCoinsLocalDataSource(context))
        coinsRepository = newRepo
        return newRepo
    }

    private fun createCoinsLocalDataSource(context: Context): CoinsLocalDataSource {
        val database = database ?: createDataBase(context)
        return CoinsLocalDataSource(database.coinDao)
    }

    private fun createDataBase(context: Context): CoinDatabase {
        val result =Room.databaseBuilder(
            context.applicationContext,
            CoinDatabase::class.java,
            "coin_database")
            .fallbackToDestructiveMigration()
            .build()
        database = result
        return result
    }
}