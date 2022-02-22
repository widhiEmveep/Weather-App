package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) : BaseViewModel(application) {

    private var _currentWeather: MutableLiveData<Result<CurrentWeatherModel>> = MutableLiveData()

    val currentWeather: LiveData<Result<CurrentWeatherModel>> get() = _currentWeather

    init {
        observeNetworkCallback()
    }

    fun getCurrentWeather(lat: Double, lon: Double) = weatherRepository.getCurrentWeather(lat, lon)
        .onEach { result -> _currentWeather.value = result }
        .launchIn(viewModelScope)

}