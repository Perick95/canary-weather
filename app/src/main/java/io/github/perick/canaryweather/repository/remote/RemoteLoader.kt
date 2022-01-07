package io.github.perick.canaryweather.repository.remote

import android.util.Log
import com.squareup.moshi.Moshi
import io.github.perick.canaryweather.repository.ForecastWeatherRequest
import io.github.perick.canaryweather.repository.ForecastWeatherResponse

object RemoteLoader {


    suspend fun getForecastWeather(forecastWeatherRequest: ForecastWeatherRequest): ForecastWeatherResponse {

        val request = ServiceBuilder.buildService(OpenWeatherService::class.java)

        try {
            val response = request.getForecastWeather(
                forecastWeatherRequest.lat,
                forecastWeatherRequest.lng,
                listOf("minutely", "hourly"),
                forecastWeatherRequest.apiKey
            )
            // Check if response was successful.
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()
                return (body!!)

            } else {
                val errorbody = response.errorBody()?.string()
                val moshi = Moshi.Builder().build()
                throw Exception()
//                val value = moshi.adapter(ApiErrorResponse::class.java).fromJson(errorbody)
//                throw ServerApiException(
//                    value?.error?.code.toString(),
//                    value?.error?.message.toString()
//                )
            }
        } catch (ex: Exception) {
            Log.d("", "")
            throw ex
        }
    }
}