package com.bekzad.cryptotracker.data.repository

import androidx.lifecycle.LiveData
import com.bekzad.cryptotracker.data.domain.Coin

interface CoinsRepository {

    val coins: LiveData<List<Coin>>
    fun searchDatabase(searchQuery: String): LiveData<List<Coin>>
    suspend fun refreshCoins(pageNumber: Int = 1)

}