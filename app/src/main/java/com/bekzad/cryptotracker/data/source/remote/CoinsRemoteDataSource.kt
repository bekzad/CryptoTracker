package com.bekzad.cryptotracker.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.CoinsDataSource
import timber.log.Timber

object CoinsRemoteDataSource : CoinsDataSource {

    /**
     * Should not be used
     */
    override suspend fun insertAllCoins(networkCoins: List<NetworkCoin>) {}

    /**
     * Should not be used
     */
    override fun observeCoins(): LiveData<List<Coin>> {
        return MutableLiveData<List<Coin>>()
    }

    /**
     * Should not be used
     */
    override fun searchDatabase(searchQuery: String): LiveData<List<Coin>> {
        return MutableLiveData<List<Coin>>()
    }

    override suspend fun getAllCoins(pageNumber: Int): List<NetworkCoin> {
        val coins = Network.api.getAllCoins(page = pageNumber).await()
        Timber.i(coins.toString())
        return coins
    }
}