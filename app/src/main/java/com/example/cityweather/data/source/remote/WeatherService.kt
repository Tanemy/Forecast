package com.example.cityweather.data.source.remote

import com.example.cityweather.data.source.remote.to.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    fun getWeatherForCity(
        @Query("appid") apiKey: String,
        @Query("q") cityName: String,
        @Query("units") units: String,
    ): Single<WeatherResponse>
}
