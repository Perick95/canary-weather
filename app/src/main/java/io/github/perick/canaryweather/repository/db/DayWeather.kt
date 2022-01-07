package io.github.perick.canaryweather.repository.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_weather_table")
data class DayWeather(

    @PrimaryKey
    @ColumnInfo(name = "id_weather")
    val idWeather: Long,

    @ColumnInfo(name = "main")
    val main: String,

    @ColumnInfo(name = "description")
    val description: String,

)
