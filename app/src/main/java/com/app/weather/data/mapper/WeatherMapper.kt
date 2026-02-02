package com.app.weather.data.mapper

import com.app.weather.data.remote.WeatherDto
import com.app.weather.domain.model.Weather

fun WeatherDto.toDomain(): Weather {
    return Weather(
        cityName = name,
        temperature = main.temp,
        description = weather.firstOrNull()?.description.orEmpty(),
        icon = weather.firstOrNull()?.icon.orEmpty(),
        country = sys.country,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
    )
}