package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.CoinsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultCoinsRepository (
    private val remoteDataSource: CoinsDataSource,
    private val localDataSource: CoinsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoinsRepository {

    /**
     * We can observe our data right from the database
     */
    override fun observeCoins(): LiveData<List<Coin>> = localDataSource.observeCoins()

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
            val coins = remoteDataSource.getAllCoins(pageNumber)
            localDataSource.insertAllCoins(coins)
        }
    }
}

