package io.github.perick.canaryweather.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.perick.canaryweather.repository.db.model.DayWeatherDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface DayWeatherDetailDao {

    @Query("SELECT * FROM day_weather_detail_table")
    fun getAllWeatherDetail(): Flow<List<DayWeatherDetail>>

    @Query("SELECT * FROM day_weather_detail_table WHERE id_weather_detail = :idWeatherDetail")
    fun getWeatherDetail(idWeatherDetail: Long): Flow<DayWeatherDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDayWeather(dayWeatherDetail: DayWeatherDetail)

    @Query("DELETE FROM day_weather_detail_table")
    suspend fun deleteAll()
}