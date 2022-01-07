package io.github.perick.canaryweather.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DayWeatherDao {

    @Query("SELECT * FROM day_weather_table")
    fun getAllWeather(): Flow<List<DayWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDayWeather(reservation: DayWeather)

    @Query("DELETE FROM day_weather_table")
    suspend fun deleteAll()
}