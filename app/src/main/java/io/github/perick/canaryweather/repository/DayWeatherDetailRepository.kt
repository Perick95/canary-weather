package io.github.perick.canaryweather.repository

import androidx.annotation.WorkerThread
import io.github.perick.canaryweather.repository.db.dao.DayWeatherDetailDao
import io.github.perick.canaryweather.repository.db.model.DayWeatherDetail
import kotlinx.coroutines.flow.Flow

class DayWeatherDetailRepository(private val dayWeatherDetailDao: DayWeatherDetailDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dayWeatherDetail: DayWeatherDetail) {
        dayWeatherDetailDao.insertDayWeather(dayWeatherDetail)
    }

    suspend fun deleteAll() {
        dayWeatherDetailDao.deleteAll()
    }

    fun getWeatherDetail(weatherDetailId: Long) : Flow<DayWeatherDetail> {
        return dayWeatherDetailDao.getWeatherDetail(weatherDetailId)
    }
}