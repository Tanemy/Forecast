package com.example.cityweather.di.qualifiers

import com.example.cityweather.data.WeatherRepository
import com.example.cityweather.data.source.WeatherRepositoryImpl
import com.example.cityweather.data.source.remote.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object WeatherModule {

    @Provides
    fun providesWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(weatherService)
    }
}