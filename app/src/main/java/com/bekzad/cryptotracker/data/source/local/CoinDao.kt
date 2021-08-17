package com.bekzad.cryptotracker.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(vararg databaseCoins: DatabaseCoin)

    @Query("SELECT * FROM coin_table ORDER BY market_cap_rank ASC")
    fun observeCoins(): LiveData<List<DatabaseCoin>>

    @Query("SELECT * FROM coin_table WHERE name LIKE :searchQuery OR symbol LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<DatabaseCoin>>

}