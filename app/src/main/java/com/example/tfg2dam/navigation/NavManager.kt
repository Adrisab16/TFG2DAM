package com.example.tfg2dam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tfg2dam.screens.defaultview.DefaultView
import com.example.tfg2dam.screens.primaryscreens.Discover
import com.example.tfg2dam.screens.primaryscreens.Home
import com.example.tfg2dam.screens.login_signupscreens.LogIn
import com.example.tfg2dam.screens.primaryscreens.MyList
import com.example.tfg2dam.screens.login_signupscreens.SignUp
import com.example.tfg2dam.screens.secondaryviews.GameDetailsScreen
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.LoginViewModel
import com.example.tfg2dam.viewmodel.UserVideogameViewModel

/**
 * Función que gestiona la navegación entre las diferentes pantallas de la aplicación.
 *
 * @param loginVM ViewModel para la autenticación y gestión de sesiones de usuario.
 * @param gameVM ViewModel para la gestión de datos de videojuegos.
 * @param userVideogameVM ViewModel para la gestión de la relación entre usuarios y videojuegos.
 */
@Composable
fun NavManager(
    loginVM: LoginViewModel,
    gameVM: VideojuegosViewModel,
    userVideogameVM: UserVideogameViewModel
    ) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Blank") {

        // Muestra la vista correspondiente a la pantalla Blank.
        composable("Blank") {
            DefaultView(navController)
        }

        // Muestra la vista correspondiente a la pantalla de registro (Sign-Up).
        composable("SignUp") {
            SignUp(navController, loginVM)
        }

        // Muestra la vista correspondiente a la pantalla de inicio de sesión (Login).
        composable("Login") {
            LogIn(navController, loginVM)
        }

        // Muestra la vista correspondiente a la pantalla principal (Home).
        composable("Home") {
            Home(navController, loginVM, gameVM)
        }

        // Muestra la vista correspondiente a la pantalla de busqueda de videojuegos (Discover).
        composable("Discover") {
            Discover(navController, loginVM, gameVM)
        }

        // Muestra la vista correspondiente a la pantalla de visualización de listas (MyList), pasandole el parámetro countlistout
        composable("MyList/{countlistout}") { backStackEntry ->
            val countlistout = backStackEntry.arguments?.getString("countlistout") ?: "0"
            MyList(navController, loginVM, userVideogameVM, countlistout)
        }

        // Muestra la vista correspondiente a la pantalla de detalles de videojuegos (GameDetailsScreen), pasandole el gameId como parámetro
        composable(
            "GameDetailsScreen/{gameId}",
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId")
            if (gameId != null) {
                GameDetailsScreen(navController, loginVM, userVideogameVM, gameId, gameVM)
            } else {
                GameDetailsScreen(navController, loginVM, userVideogameVM, 0, gameVM)
            }
        }

    }
}