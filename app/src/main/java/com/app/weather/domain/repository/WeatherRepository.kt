package com.app.weather.domain.repository

import com.app.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): Result<Weather>

    suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double
    ): Result<Weather>
}