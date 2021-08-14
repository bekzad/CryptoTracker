package com.bekzad.cryptotracker.data.source.remote

import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.local.DatabaseCoin
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * These data classes are for receiving the data from the server
 */
@JsonClass(generateAdapter = true)
data class NetworkCoin (
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @Json(name = "current_price") val currentPrice: String,
    @Json(name = "market_cap") val marketCap: Long,
    @Json(name = "price_change_percentage_24h") val priceChange: String
)

@JsonClass(generateAdapter = true)
data class NetworkCoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @Json(name = "current_price") val currentPrice: String,
    @Json(name = "market_cap") val marketCap: Long,
    @Json(name = "price_change_percentage_24h")
    val priceChange: String
)

fun List<NetworkCoin>.asDomainModel(): List<Coin> =
    this.map { Coin(
        id = it.id,
        symbol = it.symbol,
        name = it.name,
        image = it.image,
        currentPrice = it.currentPrice.toBigDecimal(),
        marketCap = it.marketCap,
        priceChange = it.priceChange.toBigDecimal())
    }

fun List<NetworkCoin>.asDatabaseModel(): Array<DatabaseCoin> =
    this.map { DatabaseCoin(
        id = it.id,
        symbol = it.symbol,
        name = it.name,
        image = it.image,
        currentPrice = it.currentPrice,
        marketCap = it.marketCap,
        priceChange = it.priceChange)
    }.toTypedArray()