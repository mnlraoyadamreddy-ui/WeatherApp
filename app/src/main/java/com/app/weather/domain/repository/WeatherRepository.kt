package com.app.weather.domain.repository

import com.app.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): Weather

    suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double
    ): Weather
}