package com.example.cityweather.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityweather.R
import com.example.cityweather.data.WeatherRepository
import com.example.cityweather.data.model.SearchTerm
import com.example.cityweather.data.model.WeatherDetail
import com.example.cityweather.di.qualifiers.UIScheduler
import com.example.cityweather.di.qualifiers.WorkScheduler
import com.example.cityweather.util.navigation.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @WorkScheduler val workScheduler: Scheduler,
    @UIScheduler val uiScheduler: Scheduler,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible:LiveData<Boolean> = _progressVisible

    private val _navigateToResults: MutableLiveData<Event<List<WeatherDetail>>> = MutableLiveData()
    val navigateToResults: LiveData<Event<List<WeatherDetail>>> = _navigateToResults

    private val _errorEvent: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorEvent: LiveData<Event<Int>> = _errorEvent

    private val _searchTerm: MutableLiveData<SearchTerm> = MutableLiveData()
    val cityName: MutableLiveData<String> = MutableLiveData()
    val isInputValid = MutableLiveData(false)

    fun onLookup() {
        _progressVisible.value = true
        _searchTerm.value?.let {
            val disposable: Disposable =
                weatherRepository.getWeatherForCity(it)
                    .subscribeOn(workScheduler)
                    .observeOn(uiScheduler)
                    .subscribeWith(object : DisposableSingleObserver<List<WeatherDetail>>() {

                        override fun onSuccess(results: List<WeatherDetail>) {
                            _progressVisible.value = false
                            _navigateToResults.value = Event(results)
                        }

                        override fun onError(e: Throwable) {
                            _progressVisible.value = false

                            _errorEvent.value = when (e) {
                                is HttpException -> {
                                    Event(handleHttpErrorCode(e.code()))
                                }
                                is UnknownHostException -> {
                                    Event(R.string.error_network)
                                }
                                else -> {
                                    Event(R.string.error_backend)
                                }
                            }
                        }
                    })
            compositeDisposable.add(disposable)
        }
    }

    fun validateInput() {
        if((cityName.value?.length?: 0) >= MIN_LENGTH) {
            isInputValid.value = true
            _searchTerm.value = SearchTerm(cityName.value?: "")
        } else {
            isInputValid.value = false
        }
    }

    private fun handleHttpErrorCode(code: Int) : Int {
        return when(code) {
            NOT_FOUND -> {
                R.string.error_city
            }
            ACCESS_DENIED -> {
                R.string.error_denied
            }
            else -> {
                R.string.error_backend
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        const val MIN_LENGTH = 3
        const val NOT_FOUND = 404
        const val ACCESS_DENIED = 401
    }
}