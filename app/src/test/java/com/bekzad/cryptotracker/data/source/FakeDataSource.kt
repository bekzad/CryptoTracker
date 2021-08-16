package com.bekzad.cryptotracker.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.remote.NetworkCoin
import com.bekzad.cryptotracker.data.source.remote.asDomainModel

/**
 * This is used to test the repository
 */
class FakeDataSource(var coins: MutableList<Coin>? = mutableListOf()) : CoinsDataSource {

    override suspend fun insertAllCoins(networkCoins: List<NetworkCoin>) {
        val result = networkCoins.asDomainModel()
        coins?.addAll(result)
    }

    override fun observeCoins(): LiveData<List<Coin>> {
        return MutableLiveData()
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<Coin>> {
        return MutableLiveData()
    }

    override suspend fun getAllCoins(pageNumber: Int): List<NetworkCoin> {
        return coins?.map {
            with(it) {
                NetworkCoin(id = id, symbol = symbol, name = name,
                    image = image, marketCap = marketCap, currentPrice = currentPrice.toString(),
                    priceChange = priceChange.toString(), marketCapRank = marketCapRank)
            }
        } ?: listOf()
    }
}