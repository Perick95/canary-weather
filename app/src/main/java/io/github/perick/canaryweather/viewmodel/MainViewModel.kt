package io.github.perick.canaryweather.viewmodel

import androidx.lifecycle.*
import io.github.perick.canaryweather.repository.DayWeatherDetailRepository
import io.github.perick.canaryweather.repository.remote.model.ForecastWeatherRequest
import io.github.perick.canaryweather.repository.remote.model.ForecastWeatherResponse
import io.github.perick.canaryweather.repository.remote.ResultWrapper
import io.github.perick.canaryweather.repository.DayWeatherRepository
import io.github.perick.canaryweather.repository.db.model.DayWeather
import io.github.perick.canaryweather.repository.db.model.DayWeatherDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val dayWeatherRepository: DayWeatherRepository,
    private val dayWeatherDetailRepository: DayWeatherDetailRepository
) : ViewModel() {

    val forecastWeather: MutableLiveData<ForecastWeatherResponse> = MutableLiveData()
    val apiError: MutableLiveData<String?> = MutableLiveData()

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWeathers: LiveData<List<DayWeather>> = dayWeatherRepository.allWeather.asLiveData()


    fun getForecastWeather(
        forecastWeatherRequest: ForecastWeatherRequest
    ) {
        viewModelScope.launch {
            val result = dayWeatherRepository.getForecastWeather(forecastWeatherRequest)
            when (result) {
                is ResultWrapper.Success -> {
                    val value = result.value
                    dayWeatherRepository.deleteAll()
                    dayWeatherDetailRepository.deleteAll()
                    value.daily.forEach {
                        insertDayWeatherDetail(
                            DayWeatherDetail(
                                it.dt, it.sunrise, it.sunset, it.moonrise, it.moonset, it.moonPhase, it.pressure, it.humidity,
                                it.dewPoint, it.windSpeed, it.windDeg, it.windGust, it.clouds, it.pop, it.rain, it.snow, it.uvi
                            )
                        )
                        insertDayWeather(DayWeather(it.dt, it.weather[0].main, it.weather[0].description, it.temp.day.toInt()))
                    }
                    forecastWeather.postValue(value)
                }
                is ResultWrapper.GenericError -> {
                    val error = result.message
                    apiError.postValue(error)
                }
            }
        }
    }

    fun insertDayWeatherDetail(dayWeatherDetail: DayWeatherDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            dayWeatherDetailRepository.insert(dayWeatherDetail)
        }
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertDayWeather(dayWeather: DayWeather) = viewModelScope.launch(Dispatchers.IO) {
        dayWeatherRepository.insert(dayWeather)
    }
}

class MainViewModelFactory(
    private val dayWeatherRepository: DayWeatherRepository,
    private val dayWeatherDetailRepository: DayWeatherDetailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dayWeatherRepository, dayWeatherDetailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}