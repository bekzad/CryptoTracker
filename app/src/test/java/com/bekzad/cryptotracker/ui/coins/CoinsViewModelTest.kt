package com.bekzad.cryptotracker.ui.coins

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bekzad.cryptotracker.MainCoroutineRule
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.repository.FakeCoinRepository
import com.bekzad.cryptotracker.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoinsViewModelTest {

    // It runs all Architecture Components-related background jobs in the same thread so that
    // the test results happen synchronously, and in a repeatable order.
    // For tests that include testing LiveData
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Swaps Dispatcher.Main for a TestCoroutineDispatcher
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val coin1 = Coin("bitcoin", 1, "btc", "Bitcoin", "url", 45600.0,
        8400000, 2.1)

    private val coin2 = Coin("ethereum", 2, "eth", "Ethereum", "url", 45600.0,
        8400000, 2.1)

    private val coin3 = Coin("cordana", 3, "crd", "Cordana", "url", 45600.0,
        8400000, 2.1)

    // Using a fake repository to be injected into the viewmodel
    private lateinit var coinRepository: FakeCoinRepository

    private lateinit var coinsViewModel: CoinsViewModel

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        coinRepository = FakeCoinRepository()

        coinRepository.addCoins(coin1, coin2, coin3)
        coinsViewModel = CoinsViewModel(coinRepository)
    }

    @Test
    fun addCoins_coinsViewModelUpdated() {
        val value = coinsViewModel.coins.getOrAwaitValue()

        assertThat(value.size, `is`(3))
        assertThat(value[0], `is`(coin1))
        assertThat(value[1], `is`(coin2))
        assertThat(value[2], `is`(coin3))
    }

}