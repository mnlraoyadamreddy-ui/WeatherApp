package com.app.weather.domain.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val country: String,
    val sunrise: Double,
    val sunset: Double,
)