package dev.kostie.bdora.api.user.dto

data class RegisterRequest(
    val username: String,
    val password: String,
    val confirmPassword: String,
)