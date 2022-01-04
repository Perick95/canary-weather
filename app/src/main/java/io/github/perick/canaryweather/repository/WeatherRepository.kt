package io.github.perick.canaryweather.repository

import kotlinx.coroutines.Dispatchers

object WeatherRepository {
    suspend fun getForecastWeather(forecastWeatherRequest: ForecastWeatherRequest): ResultWrapper<ForecastWeatherResponse> {
        return RemoteHelper.safeApiCall(Dispatchers.IO) {
            RemoteLoader.getForecastWeather(forecastWeatherRequest)
        }
    }
}