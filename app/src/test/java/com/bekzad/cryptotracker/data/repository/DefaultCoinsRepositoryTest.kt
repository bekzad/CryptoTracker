package com.bekzad.cryptotracker.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bekzad.cryptotracker.MainCoroutineRule
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.source.FakeDataSource
import com.bekzad.cryptotracker.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Testing Repository class
 */
class DefaultCoinsRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Swaps Dispatcher.Main for a TestCoroutineDispatcher
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var remoteDataSource: FakeDataSource
    private lateinit var localDataSource: FakeDataSource

    // class under test
    private lateinit var coinsRepository: DefaultCoinsRepository

    private val coin1 = Coin("bitcoin", 1, "btc",
        "Bitcoin", "url", 45600.0,
        8400000, 2.1)

    private val coin2 = Coin("ethereum", 2, "eth",
        "Ethereum", "url", 45600.0,
        8400000, 2.1)

    private val coin3 = Coin("cordana", 3, "crd",
        "Cordana", "url", 45600.0,
        8400000, 2.1)

    private val remoteCoins = listOf(coin1, coin2).sortedBy { it.id }
    private val localCoins = listOf(coin3).sortedBy { it.id }


    @Before
    fun createRepository() {
        remoteDataSource = FakeDataSource(remoteCoins.toMutableList())
        localDataSource = FakeDataSource(localCoins.toMutableList())

        coinsRepository = DefaultCoinsRepository(remoteDataSource, localDataSource,
            Dispatchers.Main)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun geCoins_requestAllCoinsFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {

        // When refreshing the repository
        coinsRepository.refreshCoins()

        // Wait for the liveData values
        val value = coinsRepository.observeCoins().getOrAwaitValue()

        assertThat(value.size, `is`(3))

    }
}