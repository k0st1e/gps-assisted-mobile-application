package dev.kostie.bdora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kostie.bdora.api.RetrofitInstance
import dev.kostie.bdora.api.user.dto.LoginRequest
import dev.kostie.bdora.api.user.dto.UserResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userApi = RetrofitInstance.userApi
    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult : LiveData<LoginState> = _loginResult

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = userApi.loginUser(LoginRequest(username, password))

                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    _loginResult.value = LoginState.Success(user)
                } else {
                    _loginResult.value = LoginState.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _loginResult.value = LoginState.Error("Network error: ${e.message}")
            }
        }
    }

    sealed class LoginState {
        data class Success(val response: UserResponse) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}