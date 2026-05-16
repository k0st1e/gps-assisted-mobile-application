package dev.kostie.bdora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kostie.bdora.api.RetrofitInstance
import dev.kostie.bdora.api.user.dto.RegisterRequest
import dev.kostie.bdora.api.user.dto.UserResponse
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val userApi = RetrofitInstance.userApi
    private val _registerResult = MutableLiveData<RegisterState>()
    val registerResult : LiveData<RegisterState> = _registerResult

    fun registerUser(username: String,
                     password: String,
                     confirmpassword: String) {

        viewModelScope.launch {
            try {
                val response = userApi.createUser(
                    RegisterRequest(
                        username,
                        password,
                        confirmpassword
                    )
                )

                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    _registerResult.value = RegisterState.Success(user)
                } else {
                    _registerResult.value = RegisterState.Error("Registration Failed")
                }
            } catch (e: Exception) {
                _registerResult.value = RegisterState.Error("Network error: ${e.message}")
            }
        }
    }

    sealed class RegisterState {
        data class Success(val response: UserResponse) : RegisterState()
        data class Error(val message: String) : RegisterState()
    }
}