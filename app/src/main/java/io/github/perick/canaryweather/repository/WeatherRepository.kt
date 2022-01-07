package io.github.perick.canaryweather.repository

import androidx.annotation.WorkerThread
import io.github.perick.canaryweather.repository.db.AppDatabase
import io.github.perick.canaryweather.repository.db.DayWeather
import io.github.perick.canaryweather.repository.db.DayWeatherDao
import io.github.perick.canaryweather.repository.remote.RemoteHelper
import io.github.perick.canaryweather.repository.remote.RemoteLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class WeatherRepository(private val dayWeatherDao: DayWeatherDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWeather: Flow<List<DayWeather>> = dayWeatherDao.getAllWeather()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dayWeather: DayWeather) {
        dayWeatherDao.insertDayWeather(dayWeather)
    }

    suspend fun getForecastWeather(forecastWeatherRequest: ForecastWeatherRequest): ResultWrapper<ForecastWeatherResponse> {
        return RemoteHelper.safeApiCall(Dispatchers.IO) {
            RemoteLoader.getForecastWeather(forecastWeatherRequest)
        }
    }
}