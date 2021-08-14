package com.bekzad.cryptotracker.ui.coins

import android.app.Application
import androidx.lifecycle.*
import com.bekzad.cryptotracker.data.repository.CoinRepository
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import kotlinx.coroutines.launch

class CoinsViewModel(app: Application) : AndroidViewModel(app) {

    private val database = CoinDatabase.getDatabase(app)
    private val repository = CoinRepository(database)

    init {
        // At initialization refresh the database
        viewModelScope.launch {
            repository.refreshCoins()
        }
    }

    val coins = repository.coins
    val coinsString: LiveData<String> = Transformations.map(coins) {
        it.toString()
    }
}

@Suppress("UNCHECKED_CAST")
class CoinsViewModelFactory (
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoinsViewModel(app) as T)
}
