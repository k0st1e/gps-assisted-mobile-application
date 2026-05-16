package dev.kostie.bdora.api

import dev.kostie.bdora.api.route.RouteApi
import dev.kostie.bdora.api.user.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASEURL = "" // Omitted in this repo.

    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(RetrofitInstance.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserApi = getInstance().create(UserApi::class.java)
    val routeApi: RouteApi = getInstance().create(RouteApi::class.java)
}