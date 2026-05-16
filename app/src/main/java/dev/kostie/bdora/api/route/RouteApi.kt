package dev.kostie.bdora.api.route

import dev.kostie.bdora.api.route.dto.SubmitRouteRequest
import dev.kostie.bdora.api.route.dto.SubmitRouteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RouteApi {
    @POST("/api/route/submit")
    suspend fun submitRoute(
        @Body request: SubmitRouteRequest
    ) : Response<SubmitRouteResponse>

//    @GET("/api/route/explore")
//    suspend fun getAllRoutes(
//
//    )
//
//    @GET("/api/route/routes")
//    suspend fun getMyRoutes(
//
//    )
}