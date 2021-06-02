package com.example.cityweather.data.source.remote.to

data class WeatherResponse (
	val list : List<WeatherWrapper>,
	val city : City
) {

	data class WeatherWrapper(
		val dt: Double,
		val main: Main,
		val weather: List<Weather>
	)

	data class Weather(
		val main: String,
		val description: String
	)

	data class Main(
		val temp: Double,
		val feels_like: Double
	)

	data class City(
		val name: String,
	)
}