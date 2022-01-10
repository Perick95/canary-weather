package io.github.perick.canaryweather.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_weather_detail_table")
data class DayWeatherDetail(
    @PrimaryKey
    @ColumnInfo(name = "id_weather_detail")
    val dt: Long,
    @ColumnInfo(name = "sunrise")
    val sunrise: Long,
    @ColumnInfo(name = "sunset")
    val sunset: Long,
    @ColumnInfo(name = "moonrise")
    val moonrise: Long,
    @ColumnInfo(name = "moonset")
    val moonset: Long,
    @ColumnInfo(name = "moon_phase")
    val moonPhase: Double,
    @ColumnInfo(name = "pressure")
    val pressure: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Double,
    @ColumnInfo(name = "dew_point")
    val dewPoint: Double,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    @ColumnInfo(name = "wind_deg")
    val windDeg: Double,
    @ColumnInfo(name = "wind_gust")
    val windGust: Double,
    @ColumnInfo(name = "clouds")
    val clouds: Double,
    @ColumnInfo(name = "pop")
    val pop: Double,
    @ColumnInfo(name = "rain")
    val rain: Double? = null,
    @ColumnInfo(name = "snow")
    val snow: Double? = null,
    @ColumnInfo(name = "uvi")
    val uvi: Double? = null
)
