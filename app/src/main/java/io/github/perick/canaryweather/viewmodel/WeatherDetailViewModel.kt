package io.github.perick.canaryweather.viewmodel

import androidx.lifecycle.*
import io.github.perick.canaryweather.repository.DayWeatherDetailRepository

import io.github.perick.canaryweather.repository.db.model.DayWeatherDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherDetailViewModel(private val dayWeatherDetailRepository: DayWeatherDetailRepository) : ViewModel
    () {

    val weatherDetail: MutableLiveData<DayWeatherDetail> = MutableLiveData()

    fun getWeatherDetail(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDetail.postValue(dayWeatherDetailRepository.getWeatherDetail(id))
        }

    }


}

class WeatherDetailViewModelFactory(private val dayWeatherDetailRepository: DayWeatherDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherDetailViewModel(dayWeatherDetailRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}