package io.github.perick.canaryweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.perick.canaryweather.repository.ForecastWeatherRequest
import io.github.perick.canaryweather.repository.ForecastWeatherResponse
import io.github.perick.canaryweather.repository.ResultWrapper
import io.github.perick.canaryweather.repository.WeatherRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val forecastWeather: MutableLiveData<ForecastWeatherResponse> = MutableLiveData()
    val apiError: MutableLiveData<String?> = MutableLiveData()


    fun getForecastWeather(
        forecastWeatherRequest: ForecastWeatherRequest
    ) {
        viewModelScope.launch {
            val result = WeatherRepository.getForecastWeather(forecastWeatherRequest)
            when (result) {
                is ResultWrapper.Success -> {
                    val value = result.value
                    forecastWeather.postValue(value)
                }
                is ResultWrapper.GenericError -> {
                    val error = result.message
                    apiError.postValue(error)
                }
            }
        }
    }
}