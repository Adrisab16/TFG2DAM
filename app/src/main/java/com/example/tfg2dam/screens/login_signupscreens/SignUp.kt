package com.example.tfg2dam.screens.login_signupscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import com.example.tfg2dam.loginheader.LoginHeader
import com.example.tfg2dam.loginheader.Property1
import com.example.tfg2dam.navsignupbutton.NavSignUpButton
import com.example.tfg2dam.platformsicons.PlatformsIcons
import com.example.tfg2dam.signupfield.SignUpField
import com.example.tfg2dam.uiresources.EmailField
import com.example.tfg2dam.uiresources.PasswordField
import com.example.tfg2dam.uiresources.UserField
import com.example.tfg2dam.viewmodel.LoginViewModel

/**
 * Función componible para la pantalla de registro (Sign-Up).
 *
 * @param navController El controlador de navegación para navegar entre componibles.
 * @param loginVM El ViewModel para manejar operaciones relacionadas con el inicio de sesión.
 */
@Composable
fun SignUp(navController: NavController, loginVM: LoginViewModel){
    val context = LocalContext.current

    // Contenedor principal que llena toda la pantalla
    Box(Modifier.fillMaxSize()) {
        // Imagen de fondo que cubre toda la pantalla
        Image(
            painter = painterResource(id = R.drawable.sign_up_background_image_1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Esto ajustará la escala de contenido para que la imagen ocupe tod0 el espacio disponible sin recortarla
        )

        // Título de la pantalla de registro (Sign-Up)
        LoginHeader(property1 = Property1.SignUpHeader, modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp))

        // Diseño del contenido del cuerpo de la pantalla
        Box(modifier = Modifier.align(Alignment.Center).padding(top = 120.dp)) {

            // Box para contener los campos de registro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .align(Alignment.Center)
            ) {
                SignUpField() // Renderiza los campos de registro
            }

            // Box para contener los iconos de las plataformas
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)) {
                PlatformsIcons()
            }

            // Box para el diseño del contenido principal
            Box(
                modifier = Modifier.align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(50.dp))

                    // Box para el campo de nombre de usuario
                    Box(modifier =  Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                    ) {
                        UserField(
                            textFieldValue = loginVM.userName,
                            onTextFieldValueChanged = { loginVM.changeUserName(it) },
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    // Box componible para el campo de correo electrónico
                    Box(modifier =  Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                    ) {
                        EmailField(
                            textFieldValue = loginVM.email,
                            onTextFieldValueChanged = { loginVM.changeEmail(it) },
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    // Box componible para el campo de contraseña
                    Box(modifier =  Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                    ) {
                        PasswordField(
                            textFieldValue = loginVM.password,
                            onTextFieldValueChanged = { loginVM.changePassword(it) },
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))

                    // Renderiza el botón de inicio de sesión
                    LogInButton(property1 = com.example.tfg2dam.loginbutton.Property1.SignUpButton, onLogInButtonClicked =  {loginVM.createUser(context) { navController.navigate("Home") }})

                    Spacer(modifier = Modifier.height(20.dp))

                    // Renderiza el botón de navegar a la pantalla de inicio de sesión (LogIn)
                    NavSignUpButton(property1 = com.example.tfg2dam.navsignupbutton.Property1.NavLogInButton, onNavButtonSignUpClicked =  {navController.navigate("LogIn")})
                }
            }
        }
    }
}