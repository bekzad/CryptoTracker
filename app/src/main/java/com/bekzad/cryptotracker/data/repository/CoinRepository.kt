package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import com.bekzad.cryptotracker.data.source.local.asDomainModel
import com.bekzad.cryptotracker.data.source.remote.Network
import com.bekzad.cryptotracker.data.source.remote.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinRepository(private val database: CoinDatabase) {

    val coins: LiveData<List<Coin>> = Transformations.map(database.coinDao.getAllCoins()) {
        it.asDomainModel()
    }

    suspend fun refreshCoins(pageNumber: Int = 1) {
        withContext(Dispatchers.IO) {
            val coins = Network.api.getAllCoins(page = pageNumber).await()
            database.coinDao.insertAllCoins(*coins.asDatabaseModel())
        }
    }
}