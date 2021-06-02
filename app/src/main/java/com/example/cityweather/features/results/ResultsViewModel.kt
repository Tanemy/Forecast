package com.example.cityweather.features.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityweather.data.model.WeatherDetail
import com.example.cityweather.util.navigation.Event

class ResultsViewModel : ViewModel() {
    private val _resultsList: MutableLiveData<List<WeatherDetail>> = MutableLiveData()
    val resultsList: LiveData<List<WeatherDetail>> = _resultsList

    private val _navigateToDetail: MutableLiveData<Event<WeatherDetail>> = MutableLiveData()
    val navigateToDetail: LiveData<Event<WeatherDetail>> = _navigateToDetail

    fun setResults(results: Array<WeatherDetail>) {
        _resultsList.value = results.toList()
    }

    fun onNavigateToDetail(detail: WeatherDetail) {
        _navigateToDetail.value = Event(detail)
    }
}
