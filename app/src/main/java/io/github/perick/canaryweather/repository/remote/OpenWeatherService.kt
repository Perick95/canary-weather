package io.github.perick.canaryweather.repository.remote

import io.github.perick.canaryweather.repository.remote.model.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.*

interface OpenWeatherService {

//    onecall?lat=35&lon=139&exclude=minutely,hourly&appid=5f7ea4896cc3a871301c0c09d04a04b2
    @GET("onecall")
    suspend fun getForecastWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: List<String>,
        @Query("appid") apiKey: String
    ): Response<ForecastWeatherResponse>
}