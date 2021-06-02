package com.example.cityweather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDetail(
    val id: Double,
    val city: String,
    val temperature: String,
    val feelsLike: String,
    val conditions: String,
    val conditionsDetail: String
): Parcelable
