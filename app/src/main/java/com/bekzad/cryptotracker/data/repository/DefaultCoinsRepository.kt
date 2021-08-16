package com.bekzad.cryptotracker.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.CoinsDataSource
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import com.bekzad.cryptotracker.data.source.local.CoinsLocalDataSource
import com.bekzad.cryptotracker.data.source.remote.CoinsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DefaultCoinsRepository constructor(app: Application) :
    CoinsRepository {

    private val localDataSource: CoinsDataSource
    private val remoteDataSource: CoinsDataSource
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    companion object {
        @Volatile
        private var INSTANCE: DefaultCoinsRepository? = null

        fun getRepository(app: Application): DefaultCoinsRepository {
            return INSTANCE ?: synchronized(this) {
                DefaultCoinsRepository(app).also {
                    INSTANCE = it
                }
            }
        }
    }

    init {
        val database = Room.databaseBuilder(
            app.applicationContext,
            CoinDatabase::class.java,
            "coin_database")
            .fallbackToDestructiveMigration()
            .build()

        remoteDataSource = CoinsRemoteDataSource
        localDataSource = CoinsLocalDataSource(database.coinDao)
    }

    /**
     * We can observe our data right from the database
     */
    override val coins: LiveData<List<Coin>> = localDataSource.observeCoins()

    /**
     * Searching the database by given query
     */
    override fun searchDatabase(searchQuery: String): LiveData<List<Coin>>{
        return localDataSource.searchDatabase(searchQuery)
    }

    /**
     * This function gets the coins from the api and inserts them into database
     */
    override suspend fun refreshCoins(pageNumber: Int) {
        withContext(ioDispatcher) {
            Timber.i("I am in repository")
            val coins = remoteDataSource.getAllCoins(pageNumber)
            Timber.i(coins.toString())
            localDataSource.insertAllCoins(coins)
        }
    }
}

