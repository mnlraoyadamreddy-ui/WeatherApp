package com.app.weather.domain.usecase

import com.app.weather.domain.model.Weather
import javax.inject.Inject
import com.app.weather.domain.repository.WeatherRepository

class GetWeatherByLocationUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ):  Result<Weather>  {
        return repository.getWeatherByLocation(
            lat = latitude,
            lon = longitude
        )
    }
}