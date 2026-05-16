package dev.kostie.bdora.model

data class Route(
    val username: String,
    val routeName: String,
    val locations: List<Location>,
)