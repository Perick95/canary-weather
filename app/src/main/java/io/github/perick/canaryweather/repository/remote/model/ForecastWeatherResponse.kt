package io.github.perick.canaryweather.repository.remote.model
data class ForecastWeatherResponse (
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,
    val current: Current,
    val daily: List<Daily>
)

data class Current (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Double,
    val visibility: Double,
    val windSpeed: Double,
    val windDeg: Double,
    val windGust: Double,
    val weather: List<Weather>
)

data class Daily (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val temp: Temp,
    val feelsLike: FeelsLike,
    val pressure: Double,
    val humidity: Double,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Double,
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Double,
    val pop: Double,
    val rain: Double? = null,
    val snow: Double? = null,
    val uvi: Double? = null
)
data class FeelsLike (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Temp (
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)
