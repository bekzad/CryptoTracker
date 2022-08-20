package com.bekzad.cryptotracker.ui.coins

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bekzad.cryptotracker.R
import com.bekzad.cryptotracker.ServiceLocator
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.repository.CoinsRepository
import com.bekzad.cryptotracker.data.repository.FakeAndroidCoinRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CoinsFragmentTest {

    private lateinit var repository: CoinsRepository

    // Swap the repository for a fake one in ServiceLocator
    @Before
    fun initRepository() {
        repository = FakeAndroidCoinRepository()
        ServiceLocator.coinsRepository = repository
    }

    @After
    fun cleanupDB() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_DisplayedInUi() {

        val coin1 = Coin("bitcoin", 1, "btc", "Bitcoin", "url", 45600.0,
            8400000, 2.1)

        // WHEN - Details fragment launched to display task

        launchFragmentInContainer<CoinsFragment>(Bundle(), R.style.AppTheme)

    }

}