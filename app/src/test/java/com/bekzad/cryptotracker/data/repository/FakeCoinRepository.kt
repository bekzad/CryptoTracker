package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bekzad.cryptotracker.data.domain.Coin
import kotlinx.coroutines.runBlocking

/**
 * This is used to test the viewModel
 */
class FakeCoinRepository() : CoinsRepository {

    var coinsServiceData: LinkedHashMap<String, Coin> = LinkedHashMap()
    private val observableCoins = MutableLiveData<List<Coin>>()

    override fun observeCoins(): LiveData<List<Coin>> {
        return observableCoins
    }

    override suspend fun refreshCoins(pageNumber: Int) {
        observableCoins.postValue(coinsServiceData.values.toList())
    }

    fun addCoins(vararg coins: Coin) {
        for (coin in coins) {
            coinsServiceData[coin.id] = coin
        }
        runBlocking { refreshCoins() }
    }

    /**
     * Should not be used
     */
    override fun searchDatabase(searchQuery: String): LiveData<List<Coin>> {
        return observableCoins
    }
}