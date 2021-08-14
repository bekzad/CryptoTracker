package com.bekzad.cryptotracker.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.remote.NetworkCoin
import java.math.BigDecimal

/**
 * These data classes are for saving to a database and retrieving
 */

@Entity(tableName = "coin_table")
data class DatabaseCoin (
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @ColumnInfo(name = "current_price")
    val currentPrice: String,
    @ColumnInfo(name = "market_cap")
    val marketCap: Long,
    @ColumnInfo(name = "price_change_percentage_24h")
    val priceChange: String
)

@Entity
data class DatabaseCoinDetail(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @ColumnInfo(name = "current_price")
    val currentPrice: String,
    @ColumnInfo(name = "market_cap")
    val marketCap: Long,
    @ColumnInfo(name = "price_change_percentage_24h")
    val priceChange: String
)

fun List<DatabaseCoin>.asDomainModel(): List<Coin> =
    this.map { Coin(
        id = it.id,
        symbol = it.symbol,
        name = it.name,
        image = it.image,
        currentPrice = it.currentPrice.toBigDecimal(),
        marketCap = it.marketCap,
        priceChange = it.priceChange.toBigDecimal())
    }