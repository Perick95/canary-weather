package io.github.perick.canaryweather.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.perick.canaryweather.repository.db.model.DayWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface DayWeatherDao {

    @Query("SELECT * FROM day_weather_table")
    fun getAllWeather(): Flow<List<DayWeather>>

    @Query("SELECT * FROM day_weather_table ORDER BY id_weather_detail")
    fun getAllWeatherOrdered(): Flow<List<DayWeather>>

    @Query("SELECT * FROM day_weather_table WHERE id_weather_detail = :idWeather")
    fun getWeather(idWeather: Long): Flow<DayWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDayWeather(dayWeather: DayWeather)

    @Query("DELETE FROM day_weather_table")
    suspend fun deleteAll()
}