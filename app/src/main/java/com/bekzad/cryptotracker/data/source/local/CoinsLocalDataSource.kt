package com.bekzad.cryptotracker.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.CoinsDataSource
import com.bekzad.cryptotracker.data.source.remote.NetworkCoin
import com.bekzad.cryptotracker.data.source.remote.asDatabaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * This is the point from which other classes should interact with database
 */
class CoinsLocalDataSource(
    private val coinDao: CoinDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoinsDataSource {

    override suspend fun insertAllCoins(networkCoins: List<NetworkCoin>) {
        coinDao.insertAllCoins(*networkCoins.asDatabaseModel())
    }

    override fun observeCoins(): LiveData<List<Coin>> {
        return Transformations.map(coinDao.observeCoins()) {
            it.asDomainModel()
        }
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<Coin>> {
        return Transformations.map(coinDao.searchDatabase(searchQuery)) {
            it.asDomainModel()
        }
    }

    /**
     * Should not be used
     */
    override suspend fun getAllCoins(pageNumber: Int): List<NetworkCoin> {
        return listOf()
    }
}