package dev.kostie.bdora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kostie.bdora.api.RetrofitInstance
import dev.kostie.bdora.api.route.dto.SubmitRouteRequest
import dev.kostie.bdora.api.route.dto.SubmitRouteResponse
import dev.kostie.bdora.model.Route
import kotlinx.coroutines.launch

class RouteViewModel : ViewModel() {

    private val routeApi = RetrofitInstance.routeApi
    private val _routeResult = MutableLiveData<RouteState>()
    val routeResult : LiveData<RouteState> = _routeResult

    fun submitRoute(route: Route) {
        viewModelScope.launch {
            try {
                val response = routeApi.submitRoute(SubmitRouteRequest(route = route))

                if (response.isSuccessful && response.body() != null) {
                    val route = response.body()!!
                    _routeResult.value = RouteState.Success(route)
                } else {
                    _routeResult.value = RouteState.Error("Error in submitting route")
                }
            } catch (e: Exception) {
                _routeResult.value = RouteState.Error("Network error: ${e.message}")
            }
        }
    }

    sealed class RouteState {
        data class Success(val response: SubmitRouteResponse) : RouteState()
        data class Error(val message: String) : RouteState()
    }
}