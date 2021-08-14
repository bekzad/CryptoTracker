package com.bekzad.cryptotracker.data.domain

import java.math.BigDecimal

/**
 * These data classes are to be used by the application
 */
data class Coin (
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: BigDecimal,
    val marketCap: Long,
    val priceChange: BigDecimal,
    )

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: BigDecimal,
    val marketCap: Long,
    val priceChange: BigDecimal,
)