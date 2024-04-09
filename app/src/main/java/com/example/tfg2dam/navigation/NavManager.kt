package com.example.tfg2dam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfg2dam.screens.BlankView
import com.example.tfg2dam.screens.Discover
import com.example.tfg2dam.screens.Home
import com.example.tfg2dam.screens.LogIn
import com.example.tfg2dam.screens.MyList
import com.example.tfg2dam.screens.Settings
import com.example.tfg2dam.screens.SignUp
import com.example.tfg2dam.viewmodel.loginViewModel

@Composable
fun NavManager(loginVM: loginViewModel) {
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
            Discover(navController, loginVM)
        }
        composable("MyList") {
            // Muestra la vista correspondiente a la pantalla principal (Home).
            MyList(navController, loginVM)
        }
        composable("Settings") {
            // Muestra la vista correspondiente a la pantalla principal (Home).
            Settings(navController)
        }
    }
}