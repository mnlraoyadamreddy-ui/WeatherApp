package com.app.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.domain.model.Weather
import com.app.weather.domain.usecase.GetWeatherByCityUseCase
import com.app.weather.domain.usecase.GetWeatherByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByCity: GetWeatherByCityUseCase,
    private val getWeatherByLocation: GetWeatherByLocationUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<Weather?>(null)
    val uiState = _uiState.asStateFlow()

    fun searchCity(city: String) {
        viewModelScope.launch {
            val weather = getWeatherByCity(city)
            _uiState.value = weather
        }
    }
    fun getWeatherLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = getWeatherByLocation(lat, lon)
        }
    }
}
