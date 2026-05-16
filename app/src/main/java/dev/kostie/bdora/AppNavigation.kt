package dev.kostie.bdora

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kostie.bdora.screen.HomeScreen
import dev.kostie.bdora.screen.LandingScreen
import dev.kostie.bdora.screen.LoginScreen
import dev.kostie.bdora.screen.SignupScreen
import dev.kostie.bdora.viewmodel.LoginViewModel
import dev.kostie.bdora.viewmodel.RegisterViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "landing") {

        // This is the screen the user lands at.
        composable(route = "landing") {
            LandingScreen(modifier, navController)
        }

        // Log-in screen.
        composable(route = "login") {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                modifier = modifier,
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        // Sign-up screen.
        composable(route = "signup") {
            val registerViewModel: RegisterViewModel = viewModel()
            SignupScreen(
                modifier = modifier,
                navController = navController,
                registerViewModel = registerViewModel
            )
        }

        // Home-page after a successful log-in.
//        composable(route = "home") {
//            HomeScreen(modifier)
//        }

        composable(route = "home/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            HomeScreen(modifier, username)
        }
    }
}