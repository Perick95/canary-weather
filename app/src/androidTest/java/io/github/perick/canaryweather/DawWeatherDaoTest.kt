package io.github.perick.canaryweather

import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.perick.canaryweather.repository.db.AppDatabase
import io.github.perick.canaryweather.repository.db.dao.DayWeatherDao
import io.github.perick.canaryweather.repository.db.model.DayWeather
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DawWeatherDaoTest {
    private lateinit var dayWeatherDao: DayWeatherDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        dayWeatherDao = db.dayWeatherDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun useAppContext() {
        testInsertDayWeather()
    }

    private fun testInsertDayWeather() {
        val dayWeather: DayWeather = DayWeather(1641900300, "Clouds", "broken clouds", 15)
        dayWeatherDao.insertDayWeather(dayWeather)
        val weatherData = dayWeatherDao.getWeather(1641900300)
        Assert.assertEquals(weatherData, weatherData)
    }
}