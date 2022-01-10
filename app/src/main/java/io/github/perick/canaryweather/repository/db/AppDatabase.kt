package io.github.perick.canaryweather.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.perick.canaryweather.repository.db.dao.DayWeatherDao
import io.github.perick.canaryweather.repository.db.dao.DayWeatherDetailDao
import io.github.perick.canaryweather.repository.db.model.DayWeather
import io.github.perick.canaryweather.repository.db.model.DayWeatherDetail

@Database(entities = arrayOf(DayWeather::class, DayWeatherDetail::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dayWeatherDao(): DayWeatherDao
    abstract fun dayWeatherDetailDao(): DayWeatherDetailDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}