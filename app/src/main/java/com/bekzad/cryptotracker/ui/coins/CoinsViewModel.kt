package com.bekzad.cryptotracker.ui.coins

import android.app.Application
import androidx.lifecycle.*
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.repository.CoinRepository
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import kotlinx.coroutines.launch

class CoinsViewModel(app: Application) : AndroidViewModel(app) {

    private val database = CoinDatabase.getDatabase(app)
    private val repository = CoinRepository(database)

    val coins: LiveData<List<Coin>> = repository.coins

//    private val _coin = MutableLiveData<List<Coin>>()
//    val coin: LiveData<List<Coin>> = _coin

    init {
        // At initialization refresh the database
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshCoins()
//            _coin.postValue(repository.getAllCoins())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CoinsViewModelFactory (
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoinsViewModel(app) as T)
}
