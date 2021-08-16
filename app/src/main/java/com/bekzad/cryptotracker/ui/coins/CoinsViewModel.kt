package com.bekzad.cryptotracker.ui.coins

import androidx.lifecycle.*
import com.bekzad.cryptotracker.data.domain.Coin
import com.bekzad.cryptotracker.data.repository.CoinsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

enum class CoinsApiStatus { LOADING, ERROR, DONE }

class CoinsViewModel(private val repository: CoinsRepository) : ViewModel() {

    private val _status = MutableLiveData<CoinsApiStatus>()
    val status: LiveData<CoinsApiStatus> = _status

    val coins: LiveData<List<Coin>> = repository.coins

    init {
        // At initialization refresh the database
        Timber.i("In viewModel refreshing")
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
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Coin>> {
        val query = "%$searchQuery%"
        return repository.searchDatabase(query)
    }

    fun alertFinished() {
        _status.value = CoinsApiStatus.DONE
    }
}

@Suppress("UNCHECKED_CAST")
class CoinsViewModelFactory (
    private val repository: CoinsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoinsViewModel(repository) as T)
}
