package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import com.bekzad.cryptotracker.data.domain.Coin

/**
 * This is used to test the viewModel
 */
class FakeCoinRepository(override val coins: LiveData<List<Coin>>) : CoinsRepository {



    override fun searchDatabase(searchQuery: String): LiveData<List<Coin>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshCoins(pageNumber: Int) {
        TODO("Not yet implemented")
    }
}