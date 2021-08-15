package com.bekzad.cryptotracker.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(vararg videos: DatabaseCoin)

    @Query("SELECT * FROM coin_table")
    fun observeCoins(): LiveData<List<DatabaseCoin>>

    @Query("SELECT * FROM coin_table")
    suspend fun getCoins(): List<DatabaseCoin>
}