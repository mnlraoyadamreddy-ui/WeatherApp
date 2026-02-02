package com.app.weather.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.weather.core.util.formatUnixTime
import com.app.weather.presentation.state.WeatherUiState
import com.app.weather.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherContent(viewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier = Modifier) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is WeatherUiState.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                CircularProgressIndicator()
            }
        }

        is WeatherUiState.Success -> {
            val weather = (uiState as WeatherUiState.Success).weather
            weather?.let {
                Row(modifier = modifier) {
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${it.icon}@2x.png",
                        contentDescription = null,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Column {
                        Text(text = it.cityName, fontSize = 22.sp)
                        Text(text = it.country)
                        Text(text = "${it.temperature}°C")
                        Text(text = it.description)
                        Text(text = "sunrise: ${formatUnixTime(it.sunrise)}")
                        Text(text = "sunset: ${formatUnixTime(it.sunset)}")
                    }
                }
            }
        }

        is WeatherUiState.Error -> {
            val message = (uiState as WeatherUiState.Error).message
            ErrorView(message)
        }

        WeatherUiState.Idle -> {
            Text("Search city to get weather")
        }
    }
}

