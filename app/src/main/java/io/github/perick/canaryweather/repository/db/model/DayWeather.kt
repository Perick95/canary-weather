package io.github.perick.canaryweather.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_weather_table")
data class DayWeather(

    @ColumnInfo(name = "id_weather_detail")
    val idWeatherDetail: Long,

    @ColumnInfo(name = "main")
    val main: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "degree")
    val degree: Int,

    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
