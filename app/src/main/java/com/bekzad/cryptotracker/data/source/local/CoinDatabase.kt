package com.bekzad.cryptotracker.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseCoin::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    abstract val coinDao: CoinDao

}