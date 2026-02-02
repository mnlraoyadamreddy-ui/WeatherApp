package com.app.weather.data.repository

import com.app.weather.BuildConfig
import com.app.weather.data.mapper.toDomain
import com.app.weather.domain.model.Weather
import com.app.weather.domain.repository.WeatherRepository
import com.app.weather.data.remote.WeatherApi
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): Result<Weather> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWeatherByCity(
                    city = city,
                    apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                ).toDomain()

                Result.success(response)

            } catch (e: HttpException) {
                Result.failure(
                    Exception(
                        when (e.code()) {
                            404 -> "City not found. Please check the name."
                            401 -> "Invalid API key."
                            else -> "Server error. Please try again later."
                        }
                    )
                )

            } catch (e: IOException) {
                Result.failure(Exception("No internet connection"))

            } catch (e: Exception) {
                Result.failure(Exception("Something went wrong"))
            }
        }
    }

    override suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double
    ): Result<Weather> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWeatherByLocation(
                    lat, lon, apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                ).toDomain()

                Result.success(response)

            } catch (e: HttpException) {
                Result.failure(
                    Exception(
                        when (e.code()) {
                            404 -> "City not found. Please check the name."
                            401 -> "Invalid API key."
                            else -> "Server error. Please try again later."
                        }
                    )
                )

            } catch (e: IOException) {
                Result.failure(Exception("No internet connection"))

            } catch (e: Exception) {
                Result.failure(Exception("Something went wrong"))
            }
        }
    }
}