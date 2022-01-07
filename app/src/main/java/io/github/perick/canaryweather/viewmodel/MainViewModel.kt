package io.github.perick.canaryweather.viewmodel

import androidx.lifecycle.*
import io.github.perick.canaryweather.repository.ForecastWeatherRequest
import io.github.perick.canaryweather.repository.ForecastWeatherResponse
import io.github.perick.canaryweather.repository.ResultWrapper
import io.github.perick.canaryweather.repository.WeatherRepository
import io.github.perick.canaryweather.repository.db.DayWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    val forecastWeather: MutableLiveData<ForecastWeatherResponse> = MutableLiveData()
    val apiError: MutableLiveData<String?> = MutableLiveData()

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWeathers: LiveData<List<DayWeather>> = weatherRepository.allWeather.asLiveData()


    fun getForecastWeather(
        forecastWeatherRequest: ForecastWeatherRequest
    ) {
        viewModelScope.launch {
            val result = weatherRepository.getForecastWeather(forecastWeatherRequest)
            when (result) {
                is ResultWrapper.Success -> {
                    val value = result.value
                    val weather = value.daily[0].weather[0]
                    insertDayWeather(DayWeather(weather.id, weather.main, weather.description))
                    forecastWeather.postValue(value)
                }
                is ResultWrapper.GenericError -> {
                    val error = result.message
                    apiError.postValue(error)
                }
            }
        }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertDayWeather(dayWeather: DayWeather) = viewModelScope.launch(Dispatchers.IO) {
        weatherRepository.insert(dayWeather)
    }
}

class MainViewModelFactory(private val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}