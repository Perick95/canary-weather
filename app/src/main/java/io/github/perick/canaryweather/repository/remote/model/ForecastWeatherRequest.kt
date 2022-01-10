package io.github.perick.canaryweather.repository.remote.model

/**
 * object with all the parameter for the weather forecast request
 * @param apiKey has to moved outside of this object and inserted in all api request from the service builder
 */
data class ForecastWeatherRequest(
    val lat: String,
    val lng: String,
    val daysPeriod: String,
    val apiKey: String
)
