package com.example.tfg2dam.screens.blankview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

/**
 * Composable que muestra una vista en blanco mientras se verifica el estado de autenticación del usuario.
 *
 * @param navController Controlador de navegación utilizado para cambiar entre pantallas.
 */
@Composable
fun BlankView(navController: NavController){
    // Se utiliza LaunchedEffect para ejecutar el código de forma asincrónica cuando el componente se inicializa.
    LaunchedEffect(Unit){
        // Verifica si hay un usuario autenticado en Firebase.
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            // Si hay un usuario autenticado, navega a la pantalla principal (Home).
            navController.navigate("Home")
        }else{
            // Si no hay un usuario autenticado, navega a la pantalla de inicio de sesión (Login).
            navController.navigate("Login")
        }
    }
}