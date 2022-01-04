package io.github.perick.canaryweather.repository
data class ForecastWeatherResponse (
    val cod: String,
    val cnt: Int,
    val list: List<Day>
)

data class Day(
    val dt: Int,
    val humidity: Int,
    val weather: List<Weather>
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String
)