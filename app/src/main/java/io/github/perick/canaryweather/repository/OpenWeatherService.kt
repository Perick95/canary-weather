package io.github.perick.canaryweather.repository

import retrofit2.Response
import retrofit2.http.*

interface OpenWeatherService {

    @GET("forecast/daily?lat=35&lon=139&cnt=10&appid={API key}")
    suspend fun getForecastWeather(
        @Query("lat") lat: String,
        @Query("lng") lon: String,
        @Query("cnt") days: String,
        @Query("appid") apiKey: String
    ): Response<ForecastWeatherResponse>
}