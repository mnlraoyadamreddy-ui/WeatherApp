package com.app.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.domain.usecase.GetWeatherByCityUseCase
import com.app.weather.domain.usecase.GetWeatherByLocationUseCase
import com.app.weather.presentation.state.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByCity: GetWeatherByCityUseCase,
    private val getWeatherByLocation: GetWeatherByLocationUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun searchCity(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
          getWeatherByCity(city).onSuccess {
                    _uiState.value = WeatherUiState.Success(it)
                }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(
                        error.message ?: "Unexpected error occurred"
                    )
                }
        }
    }
    fun getWeatherLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            getWeatherByLocation(lat, lon).onSuccess {
                _uiState.value = WeatherUiState.Success(it)
            }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(
                        error.message ?: "Unexpected error occurred"
                    )
                }
        }
    }
}
