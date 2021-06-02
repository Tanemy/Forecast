package com.example.cityweather.data.source

import com.example.cityweather.BuildConfig
import com.example.cityweather.data.WeatherRepository
import com.example.cityweather.data.source.remote.WeatherService
import com.example.cityweather.data.source.remote.to.WeatherResponse
import com.example.cityweather.data.model.SearchTerm
import com.example.cityweather.data.model.WeatherDetail
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
): WeatherRepository {
    override fun getWeatherForCity(searchTerm: SearchTerm): Single<List<WeatherDetail>> {
        return weatherService.getWeatherForCity(
            BuildConfig.API_KEY,
            searchTerm.cityName,
            searchTerm.units
        ).map(::mapToWeatherDetail)
    }

    private fun mapToWeatherDetail(
        weatherResponse: WeatherResponse
    ): List<WeatherDetail> {
        val city = weatherResponse.city.name
        return weatherResponse.list.map {
            WeatherDetail(
                it.dt,
                city,
                it.main.temp.toString(),
                it.main.feels_like.toString(),
                it.weather.first().main,
                it.weather.first().description
            )
        }
    }
}
