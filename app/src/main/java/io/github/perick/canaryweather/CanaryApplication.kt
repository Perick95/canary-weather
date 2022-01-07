package io.github.perick.canaryweather

import android.app.Application
import io.github.perick.canaryweather.repository.WeatherRepository
import io.github.perick.canaryweather.repository.db.AppDatabase

class CanaryApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
//    val database = AppDatabase.getDatabase(this)
    val weatherRepository by lazy { WeatherRepository(database.dayWeatherDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}