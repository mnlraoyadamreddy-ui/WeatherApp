package com.app.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.weather.core.util.formatUnixTime
import com.app.weather.presentation.viewmodel.WeatherViewModel
import com.app.weather.ui.theme.WeatherTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    @RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]) { isGranted ->
            if (isGranted) {
                fetchLocationWeather()
            }
        }
    @RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        requestLocationPermission()

        setContent {
            WeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // ✅ Permission already granted
                fetchLocationWeather()
            }

            else -> {
                // 🚀 THIS triggers permission dialog
                locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun fetchLocationWeather() {
        val viewModel: WeatherViewModel by viewModels()
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                viewModel.getWeatherLocation(
                    it.latitude,
                    it.longitude
                )
            }
        }
    }
}



@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val weather by viewModel.uiState.collectAsState()

    var city by remember { mutableStateOf("") }

    Column(modifier = modifier) {
    Row(modifier = modifier) {

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter US City") }
                ,modifier = Modifier.padding(     start = 4.dp ,   end = 4.dp)
        )

        Button(onClick = { viewModel.searchCity(city) }) {
            Text("Search")
        }
    }
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
}
