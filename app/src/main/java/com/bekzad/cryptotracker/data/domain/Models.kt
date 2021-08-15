package com.bekzad.cryptotracker.data.domain

/**
 * These data classes are to be used by the application
 */
data class Coin (
    val id: String,
    val marketCapRank: Int,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val priceChange: Double
    )

data class CoinDetail(
    val id: String,
    val marketCapRank: Int,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val priceChange: Double,
)