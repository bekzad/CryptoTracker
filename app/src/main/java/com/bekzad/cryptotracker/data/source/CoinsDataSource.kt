package com.bekzad.cryptotracker.data.source

import androidx.lifecycle.LiveData
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.remote.NetworkCoin

interface CoinsDataSource {

    suspend fun insertAllCoins(networkCoins: List<NetworkCoin>)

    fun observeCoins(): LiveData<List<Coin>>

    fun searchDatabase(searchQuery: String): LiveData<List<Coin>>

    suspend fun getAllCoins(pageNumber: Int): List<NetworkCoin>

}