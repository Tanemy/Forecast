package com.example.cityweather.data

import com.example.cityweather.data.model.SearchTerm
import com.example.cityweather.data.model.WeatherDetail
import io.reactivex.Single

interface WeatherRepository {

    fun getWeatherForCity(
        searchTerm: SearchTerm
    ): Single<List<WeatherDetail>>
}
