package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.base.BaseViewModel
import com.example.weatherapp.data.local.entity.FavoriteCity
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import com.example.weatherapp.data.remote.model.forecast.ForecastWeathersModel
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) : BaseViewModel(application) {

    private var _currentWeather: MutableLiveData<Result<CurrentWeatherModel>> = MutableLiveData()
    private var _weatherByCityName: MutableLiveData<Result<CurrentWeatherModel>> = MutableLiveData()
    private var _forecastWeather: MutableLiveData<Result<ForecastWeathersModel>> = MutableLiveData()
    private var _favCity: MutableLiveData<List<FavoriteCity>> = MutableLiveData()

    val currentWeather: LiveData<Result<CurrentWeatherModel>> get() = _currentWeather
    val weatherByCityName: LiveData<Result<CurrentWeatherModel>> get() = _weatherByCityName
    val forecastWeather: LiveData<Result<ForecastWeathersModel>> get() = _forecastWeather
    val favCity: LiveData<List<FavoriteCity>> get() = _favCity

    init {
        observeNetworkCallback()
    }

    fun getCurrentWeather(lat: Double, lon: Double) = weatherRepository.getCurrentWeather(lat, lon)
        .onEach { result -> _currentWeather.value = result }
        .launchIn(viewModelScope)

    fun getWeatherByCity(cityName: String) = weatherRepository.getWeatherByCity(cityName)
        .onEach { result -> _weatherByCityName.value = result }
        .launchIn(viewModelScope)

    fun getForecastWeather(lat: Double, lon: Double) =
        weatherRepository.getForecastWeather(lat, lon)
            .onEach { result -> _forecastWeather.value = result }
            .launchIn(viewModelScope)

    fun getFavCityResult() =
        weatherRepository.getFavCityResult()
            .onEach { result -> _favCity.value = result }
            .launchIn(viewModelScope)

    fun insertFavCityResult(favoriteCity: FavoriteCity) = viewModelScope.launch {
        try {
            weatherRepository.insertFavCityResult(favoriteCity)
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }

    fun deleteFavCityResult(cityName: String) = viewModelScope.launch {
        try {
            weatherRepository.deleteFavCityResult(cityName)
        } catch (e: Exception) {
            Timber.e(e.toString())
        }
    }
}