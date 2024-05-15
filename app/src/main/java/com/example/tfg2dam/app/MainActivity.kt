package com.example.tfg2dam.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tfg2dam.navigation.NavManager
//import com.example.tfg2dam.MainScreen
import com.example.tfg2dam.ui.theme.TFG2DAMTheme
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
//import com.example.tfg2dam.viewmodel.VideogamesViewModel
import com.example.tfg2dam.viewmodel.loginViewModel
import com.example.tfg2dam.viewmodel.userVideogameViewModel
import com.google.firebase.FirebaseApp

/**
 * Actividad principal de la aplicación, se encarga de inicializar
 * Firebase y los ViewModels necesarios para la navegación y gestión de datos de usuario
 * y videojuegos.
 */

class MainActivity : ComponentActivity() {
    /**
     * Se llama cuando la actividad es creada. Inicializa Firebase y configura los ViewModels
     * utilizados en la aplicación.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa Firebase en el contexto de esta actividad.
        FirebaseApp.initializeApp(this)

        // Inicializa el ViewModel para la autenticación, gestión y registro de sesiones de usuario.
        val loginVM: loginViewModel by viewModels()

        // Inicializa el ViewModel para la gestión de datos de videojuegos.
        val gameVM: VideojuegosViewModel by viewModels()

        // Inicializa el ViewModel para la gestión de las listas de videojuegos asociadas alos usuarios.
        val userVideogameVM: userVideogameViewModel by viewModels()

        // Configura la interfaz de usuario utilizando Jetpack Compose.
        setContent {
            // Aplica el tema TFG2DAM a la aplicación.
            TFG2DAMTheme {
                // Contenedor Surface que ocupa tod0 el tamaño de la pantalla y usa el color
                // de fondo definido en el tema.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Gestiona la navegación dentro de la aplicación, pasando los ViewModels
                    // necesarios para las diferentes pantallas.
                    NavManager(loginVM, gameVM, userVideogameVM)
                }
            }
        }
    }
}