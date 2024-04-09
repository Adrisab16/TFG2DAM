package com.example.tfg2dam.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.tfg2dam.viewmodel.loginViewModel


@Composable
fun LogIn(navController: NavController, loginVM: loginViewModel) {

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background_login_background_photo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Esto ajustará la escala de contenido para que la imagen ocupe tod0 el espacio disponible sin recortarla
        )

        // Titulo de la pantalla de LogIn
        LoginHeader(property1 = Property1.Default, modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp))

        Box(modifier = Modifier.align(Alignment.Center).padding(top=60.dp)) {

            LogInField()

            // Iconos de plataformas de arriba
            Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)) {
                PlatformsIcons()
            }

            Box(
                modifier = Modifier.align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    // Introduccion de email:
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

                    // Introduccion de contraseña:
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

                    // Botón Login:
                    LogInButton(onLogInButtonClicked =  {loginVM.login { navController.navigate("Home") }})
                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón para ir al SignUp:
                    NavSignUpButton(onNavButtonSignUpClicked = {navController.navigate("SignUp")})
                }
            }
        }
    }
}


