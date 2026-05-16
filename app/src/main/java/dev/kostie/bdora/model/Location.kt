package dev.kostie.bdora.model

import com.mapbox.geojson.Point
import java.time.LocalDateTime

data class Location(
    val coordinate: Point,
    val locationDescription: String,
    val timestamp: LocalDateTime,
)