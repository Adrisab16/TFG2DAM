package com.example.tfg2dam.screens.login_signupscreens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg2dam.R
import com.example.tfg2dam.loginbutton.LogInButton
import com.example.tfg2dam.loginfield.LogInField
import com.example.tfg2dam.loginheader.LoginHeader
import com.example.tfg2dam.loginheader.Property1
import com.example.tfg2dam.navsignupbutton.NavSignUpButton
import com.example.tfg2dam.platformsicons.PlatformsIcons
import com.example.tfg2dam.uiresources.EmailField
import com.example.tfg2dam.uiresources.PasswordField
import com.example.tfg2dam.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

/**
 * Composable que muestra la pantalla de inicio de sesión (Login) con campos de entrada de email y contraseña.
 *
 * @param navController Controlador de navegación utilizado para cambiar entre pantallas.
 * @param loginVM ViewModel para la autenticación y gestión de sesiones de usuario.
 */
@Composable
fun LogIn(navController: NavController, loginVM: LoginViewModel) {

    // Alcance de la corrutina para la gestión de operaciones asíncronas
    val coroutineScope = rememberCoroutineScope()

    // Contexto de la aplicación obtenido del LocalContext
    val context = LocalContext.current

    // Contenedor principal
    Box(Modifier.fillMaxSize()) {
        // Fondo de pantalla de inicio de sesión
        Image(
            painter = painterResource(id = R.drawable.login_background_login_background_photo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Esto ajustará la escala de contenido para que la imagen ocupe tod0 el espacio disponible sin recortarla
        )

        // Título de la pantalla de inicio de sesión
        LoginHeader(property1 = Property1.Default, modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp))

        // Contenedor principal de la pantalla de inicio de sesión
        Box(modifier = Modifier.align(Alignment.Center).padding(top=60.dp)) {

            // Campo de entrada de inicio de sesión
            LogInField()

            // Iconos decorativos de las plataformas de consolas que aparecen en la parte de arriba
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)) {
                PlatformsIcons()
            }

            // Contenedor central
            Box(
                modifier = Modifier.align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    // Campo de entrada de email
                    Box(modifier =  Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .width(300.dp)
                    ) {
                        EmailField(
                            textFieldValue = loginVM.email,
                            onTextFieldValueChanged = { loginVM.changeEmail(it) },
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    // Campo de entrada de contraseña
                    Box(modifier =  Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .width(300.dp)
                    ) {
                        PasswordField(
                            textFieldValue = loginVM.password,
                            onTextFieldValueChanged = { loginVM.changePassword(it) },
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))

                    // Botón de inicio de sesión
                    LogInButton(onLogInButtonClicked = {
                        loginVM.login(
                            onSuccess = { navController.navigate("Home") },
                            onError = { errorMessage ->
                                // Muestra el Toast en caso de error
                                coroutineScope.launch {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    })
                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón para ir a la pantalla de registro (SignUp)
                    NavSignUpButton(onNavButtonSignUpClicked = {navController.navigate("SignUp")})
                }
            }
        }
    }
}