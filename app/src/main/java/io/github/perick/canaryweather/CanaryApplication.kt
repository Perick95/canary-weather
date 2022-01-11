package io.github.perick.canaryweather

import android.app.Application
import io.github.perick.canaryweather.repository.DayWeatherDetailRepository
import io.github.perick.canaryweather.repository.DayWeatherRepository
import io.github.perick.canaryweather.repository.db.AppDatabase

class CanaryApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val weatherRepository by lazy { DayWeatherRepository(database.dayWeatherDao()) }
    val weatherDetailRepository by lazy { DayWeatherDetailRepository(database.dayWeatherDetailDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}