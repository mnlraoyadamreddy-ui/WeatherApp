package com.app.weather.domain.usecase

import com.app.weather.domain.model.Weather
import com.app.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String):  Result<Weather>  {
        return repository.getWeatherByCity(city)
    }
}