package com.app.weather.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.weather.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel(), modifier: Modifier = Modifier) {

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
        WeatherContent(modifier = modifier)

    }
}
