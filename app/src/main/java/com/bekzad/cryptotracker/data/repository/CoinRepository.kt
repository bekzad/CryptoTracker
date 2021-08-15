package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Database
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import com.bekzad.cryptotracker.data.source.local.DatabaseCoin
import com.bekzad.cryptotracker.data.source.local.asDomainModel
import com.bekzad.cryptotracker.data.source.remote.Network
import com.bekzad.cryptotracker.data.source.remote.asDatabaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinRepository(private val database: CoinDatabase,
                     private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    val coins: LiveData<List<Coin>> = Transformations.map(database.coinDao.observeCoins()) {
        it.asDomainModel()
    }

//    suspend fun getAllCoins(): List<Coin> {
//        withContext(ioDispatcher) {
//            Transformations.map(database.coinDao.getCoins()) {
//                it.asDomainModel()
//            }
//        }
//    }

    /**
     * this function gets the coins from the database and inserts them into database
     */
    suspend fun refreshCoins(pageNumber: Int = 1) {
        withContext(ioDispatcher) {
            val coins = Network.api.getAllCoins(page = pageNumber).await()
            database.coinDao.insertAllCoins(*coins.asDatabaseModel())
        }
    }
}