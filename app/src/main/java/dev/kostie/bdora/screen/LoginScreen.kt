package dev.kostie.bdora.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.kostie.bdora.viewmodel.LoginViewModel
import dev.kostie.bdora.viewmodel.LoginViewModel.LoginState

@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                navController: NavHostController,
                loginViewModel: LoginViewModel) {

    // Need to remember the fields of username and password.
    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val loginResult = loginViewModel.loginResult.observeAsState()

    val context = LocalContext.current // ???

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Start your journey now
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Login into your account",
            style = TextStyle(
                fontSize = 26.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                // textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Create an Account
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome back!",
            style = TextStyle(
                fontSize = 20.sp,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Username Field
        OutlinedTextField(
            value = username,

            onValueChange = {
                username = it
            },

            label = {
                Text(text = "Username")
            },

            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Password Field
        OutlinedTextField(
            value = password,

            onValueChange = {
                password = it
            },

            label = {
                Text(text = "Password")
            },

            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                autoCorrectEnabled = false,
                imeAction = ImeAction.Done
            ),

            modifier = Modifier.fillMaxWidth(),

            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Log-in Action Button
        Button(onClick = {
            loginViewModel.loginUser(username, password)
        },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(
                text = "Log-in",
                fontFamily = FontFamily.SansSerif,
                fontSize = 22.sp,
            )
        }
    }

    when (val state = loginResult.value) {
        is LoginState.Success -> {
            val username = state.response.username
            navController.navigate("home/$username")
        }
        is LoginState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        null -> {}
    }
}