package com.app.weather.data.remote

data class WeatherDto(
    val name: String,
    val main: MainDto,
    val weather: List<WeatherItemDto>,
    val wind: WindDto,
    val sys: Sys
)

data class Sys(
    val country: String,
    val sunrise: Double,
    val sunset: Double
)

data class MainDto(
    val temp: Double,
    val humidity: Int
)

data class WeatherItemDto(
    val description: String,
    val icon: String
)

data class WindDto(
    val speed: Double
)