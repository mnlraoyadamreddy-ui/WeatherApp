package com.app.weather.domain.repository

import com.app.weather.data.mapper.toDomain
import com.app.weather.data.remote.WeatherApi
import com.app.weather.domain.model.Weather
import com.app.weather.BuildConfig
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository{
    private companion object {
        const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY
    }
    override suspend fun getWeatherByCity(city: String): Weather {
        return api.getWeatherByCity(city, apiKey = API_KEY).toDomain()
    }

    override suspend fun getWeatherByLocation(lat: Double, lon: Double): Weather {
        return api.getWeatherByLocation(lat, lon, apiKey = API_KEY).toDomain()
    }
}

