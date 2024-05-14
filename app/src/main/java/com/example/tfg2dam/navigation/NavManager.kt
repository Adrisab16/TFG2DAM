package com.example.tfg2dam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tfg2dam.screens.blankview.BlankView
import com.example.tfg2dam.screens.primaryscreens.Discover
import com.example.tfg2dam.screens.primaryscreens.Home
import com.example.tfg2dam.screens.login_signupscreens.LogIn
import com.example.tfg2dam.screens.primaryscreens.MyList
import com.example.tfg2dam.screens.secondaryviews.Settings
import com.example.tfg2dam.screens.login_signupscreens.SignUp
import com.example.tfg2dam.screens.secondaryviews.GameDetailsScreen
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel
import com.example.tfg2dam.viewmodel.userVideogameViewModel

@Composable
fun NavManager(
    loginVM: loginViewModel, /*gameVM: VideogamesViewModel*/
    gameVM: VideojuegosViewModel,
    userVideogameVM: userVideogameViewModel
    ) {
    // DCS - Configuración del sistema de navegación y definición de las rutas.

    // Se crea el controlador de navegación que recordará el estado de la navegación.
    val navController = rememberNavController()

    // Se configuran las rutas y se asocian con las respectivas funciones que muestran las vistas.
    NavHost(navController = navController, startDestination = "Blank") {
        composable("Blank") {
            // Muestra la vista correspondiente a la pantalla Blank.
            BlankView(navController)
        }
        composable("SignUp") {
            // Muestra la vista correspondiente a la pantalla de registro (Sign-Up).
            SignUp(navController, loginVM)
        }
        composable("Login") {
            // Muestra la vista correspondiente a la pantalla de inicio de sesión (Login).
            LogIn(navController, loginVM)
        }
        composable("Home") {
            // Muestra la vista correspondiente a la pantalla principal (Home).
            Home(navController, loginVM)
        }
        composable("Discover") {
            // Muestra la vista correspondiente a la pantalla principal (Home).
            Discover(navController, loginVM, gameVM)
        }
        composable("MyList/{countlistout}") { backStackEntry ->
            val countlistout = backStackEntry.arguments?.getString("countlistout") ?: "0"
            MyList(navController, loginVM, userVideogameVM, gameVM, countlistout)
        }
        composable(
            "GameDetailsScreen/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId")
            if (gameId != null) {
                GameDetailsScreen(navController, loginVM, userVideogameVM, gameId, gameVM)
            } else {
                // Manejar caso en el que no se recibe el ID del juego
            }
        }
        composable("Settings") {
            // Muestra la vista correspondiente a la pantalla principal (Home).
            Settings(navController, loginVM)
        }
    }
}