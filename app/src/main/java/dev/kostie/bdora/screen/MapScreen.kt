package dev.kostie.bdora.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import dev.kostie.bdora.R
import dev.kostie.bdora.model.Location
import dev.kostie.bdora.model.Route
import dev.kostie.bdora.viewmodel.RouteViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun MapScreen(modifier: Modifier = Modifier,
              username: String,
              routeViewModel: RouteViewModel = viewModel()
) {

    // Pins, when a user visits places.
    val pins = remember { mutableStateListOf<Location>() }

    // Place. Used by a text-field. A user can write something about a place that he has visited.
    var place by remember {
        mutableStateOf("")
    }

    // Title. Used by text-field. A user can write something about the whole route when he finishes.
    var title by remember {
        mutableStateOf("")
    }

    // Compass
    val geoLocation = remember { Geolocator.mobile() }
    val scope = rememberCoroutineScope()

    MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = rememberMapViewportState {
            setCameraOptions {
                // Camera is set to Greece, zoomed-out, Hard-coded for now.
                zoom(5.18)
                center(Point.fromLngLat(23.75192, 38.04173))
                pitch(0.0)
                bearing(0.0)
            }
        },
        scaleBar = {
            ScaleBar(Modifier.padding(top = 60.dp))
        },
        logo = {
            Logo(Modifier.padding(bottom = 40.dp))
        },
        attribution = {
            Attribution(Modifier.padding(bottom = 40.dp))
        }

    ) {
        // Drawables Red Marker, used by Pins in the Map.
        val redMarker = rememberIconImage(R.drawable.red_marker)

        // For each pin in pins make a PointAnnotation to be shown in the Map.
        pins.forEach { location ->
            PointAnnotation(point = location.coordinate) {
                iconImage = redMarker
                textField = location.locationDescription
                textAnchor = TextAnchor.TOP
                textOffset = listOf(0.0, 0.75)
            }
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,

    ) {

        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // Text-field to write something about the place visited.
            OutlinedTextField(
                value = place,

                onValueChange = {
                    place = it
                },

                label = {
                    Text(text = "Add Pin Name")
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedLabelColor = Black,
                    unfocusedLabelColor = Black,
                    focusedContainerColor = Color.White.copy(alpha = 0.7f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.7f),
                ),

                modifier = Modifier.fillMaxWidth(0.8f),
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Add new pin Logic when a user arrives somewhere.
            IconButton(
                onClick = {

                scope.launch {

                    when (val result = geoLocation.current(Priority.HighAccuracy)) {

                        // When the Geolocation is successful -> Create new Points in the point list.
                        is GeolocatorResult.Success -> {

                            val newLocation = Location(

                                coordinate = Point.fromLngLat(
                                    result.data.coordinates.longitude,
                                    result.data.coordinates.latitude
                                ),

                                locationDescription = place,

                                timestamp = LocalDateTime.now()
                            )
                            pins.add(newLocation)
                        }

                        is GeolocatorResult.Error -> when (result) {
                            is GeolocatorResult.NotFound -> println("Not found: ${result.message}")
                            is GeolocatorResult.GeolocationFailed -> println("Geolocation failed: ${result.message}")
                            is GeolocatorResult.PermissionDenied -> println("Permission denied: ${result.message}")
                            is GeolocatorResult.NotSupported -> println("Not supported: ${result.message}")
                            else -> println("Error: ${result.message}")
                        }
                    }
                }

            },

                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White.copy(alpha = 0.85f), CircleShape)

            ) {
                Icon(imageVector = Icons.Default.Place,
                    tint = Red,
                    contentDescription = "Add Pin")
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // Write the title of the Route text field.
            OutlinedTextField(
                value = title,

                onValueChange = {
                    title = it
                },

                label = {
                    Text(text = "Submit your Route!")
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedLabelColor = Black,
                    unfocusedLabelColor = Black,
                    focusedContainerColor = Color.White.copy(alpha = 0.7f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.7f),
                ),

                modifier = Modifier.fillMaxWidth(0.8f),
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Submit Route Logic.
            IconButton(onClick = {
                val route = Route(
                    username = username,
                    routeName = title,
                    locations = pins.toList(),
                )
                routeViewModel.submitRoute(route)
            },

                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White.copy(alpha = 0.85f), CircleShape)

            ) {
                Icon(imageVector = Icons.Default.Add,
                    tint = Green,
                    contentDescription = "Add Route")
            }
        }
    }
}