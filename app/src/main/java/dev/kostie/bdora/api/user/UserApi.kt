package dev.kostie.bdora.api.user

import dev.kostie.bdora.api.user.dto.LoginRequest
import dev.kostie.bdora.api.user.dto.RegisterRequest
import dev.kostie.bdora.api.user.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/api/user/register")
    suspend fun createUser(
        @Body request: RegisterRequest
    ) : Response<UserResponse>
    @POST("/api/user/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ) : Response<UserResponse>
}