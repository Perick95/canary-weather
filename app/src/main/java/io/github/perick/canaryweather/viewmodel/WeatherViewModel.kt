package io.github.perick.canaryweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import io.github.perick.canaryweather.repository.DayWeatherRepository
import io.github.perick.canaryweather.repository.db.model.DayWeather

class WeatherViewModel(weatherId: Long, dayWeatherRepository: DayWeatherRepository) : ViewModel() {

    val weather: LiveData<DayWeather> = dayWeatherRepository.getWeather(weatherId).asLiveData()
}

class WeatherViewModelFactory(private val weatherId: Long, private val dayWeatherRepository: DayWeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(weatherId, dayWeatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}