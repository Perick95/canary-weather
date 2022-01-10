package io.github.perick.canaryweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelStore: ViewModel() {

    val weatherIdSelected: MutableLiveData<Long> = MutableLiveData()
}