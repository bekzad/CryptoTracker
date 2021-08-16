package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import com.bekzad.cryptotracker.data.source.local.asDomainModel
import com.bekzad.cryptotracker.data.source.remote.Network
import com.bekzad.cryptotracker.data.source.remote.asDatabaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinRepository(private val database: CoinDatabase,
                     private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    /**
     * We can observe our data right from the database
     */
    val coins: LiveData<List<Coin>> = Transformations.map(database.coinDao.observeCoins()) {
        it.asDomainModel()
    }

    /**
     * Searching the database by given query
     */
    fun searchDatabase(searchQuery: String): LiveData<List<Coin>>{
        return Transformations.map(database.coinDao.searchDatabase(searchQuery)) {
            it.asDomainModel()
        }
    }

    /**
     * This function gets the coins from the api and inserts them into database
     */
    suspend fun refreshCoins(pageNumber: Int = 1) {
        withContext(ioDispatcher) {
            val coins = Network.api.getAllCoins(page = pageNumber).await()
            database.coinDao.insertAllCoins(*coins.asDatabaseModel())
        }
    }
}