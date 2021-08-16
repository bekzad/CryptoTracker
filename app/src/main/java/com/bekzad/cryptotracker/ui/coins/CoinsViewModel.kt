package com.bekzad.cryptotracker.ui.coins

import android.app.Application
import androidx.lifecycle.*
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.repository.CoinRepository
import com.bekzad.cryptotracker.data.source.local.CoinDatabase
import kotlinx.coroutines.launch

enum class CoinsApiStatus { LOADING, ERROR, DONE }

class CoinsViewModel(app: Application) : AndroidViewModel(app) {

    private val database = CoinDatabase.getDatabase(app)
    private val repository = CoinRepository(database)

    private val _status = MutableLiveData<CoinsApiStatus>()
    val status: LiveData<CoinsApiStatus> = _status

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    val coins: LiveData<List<Coin>> = repository.coins

//    private val _coin = MutableLiveData<List<Coin>>()
//    val coin: LiveData<List<Coin>> = _coin

    init {
        // At initialization refresh the database
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _status.value = CoinsApiStatus.LOADING
            try {
                repository.refreshCoins()
                _status.value = CoinsApiStatus.DONE

            } catch(error: Throwable) {
                _status.value = CoinsApiStatus.ERROR
            }

            //            _coin.postValue(repository.getAllCoins())

        }
    }

    fun alertFinished() {
        _status.value = CoinsApiStatus.DONE
    }
}

@Suppress("UNCHECKED_CAST")
class CoinsViewModelFactory (
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoinsViewModel(app) as T)
}
