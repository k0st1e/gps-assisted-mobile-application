package dev.kostie.bdora.api.user.dto

data class LoginRequest(
    val username: String,
    val password: String,
)