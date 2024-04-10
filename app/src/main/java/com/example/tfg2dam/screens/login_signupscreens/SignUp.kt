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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg2dam.alerts.Alert
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
import com.example.tfg2dam.viewmodel.loginViewModel

@Composable
fun SignUp(navController: NavController, loginVM: loginViewModel){

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sign_up_background_image_1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Esto ajustará la escala de contenido para que la imagen ocupe tod0 el espacio disponible sin recortarla
        )

        // Titulo de la pantalla de LogIn
        LoginHeader(property1 = Property1.SignUpHeader, modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp))

        Box(modifier = Modifier.align(Alignment.Center).padding(top = 120.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .align(Alignment.Center)
            ) {
                SignUpField()
            }

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
                    Spacer(modifier = Modifier.height(50.dp))
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
                    LogInButton(property1 = com.example.tfg2dam.loginbutton.Property1.SignUpButton, onLogInButtonClicked =  {loginVM.createUser { navController.navigate("Home") }})
                    Spacer(modifier = Modifier.height(20.dp))
                    NavSignUpButton(property1 = com.example.tfg2dam.navsignupbutton.Property1.NavLogInButton, onNavButtonSignUpClicked =  {navController.navigate("LogIn")})
                }
            }
        }
        // Se muestra un diálogo de alerta si [LoginViewModel.showAlert] es true.
        if (loginVM.showAlert) {
            Alert(
                title = "Alerta",
                message = "Usuario no creado correctamente",
                confirmText = "Aceptar",
                onConfirmClick = { loginVM.closeAlert() },
                onDismissClick = { }
            ) // DCS - ninguna acción en onDismissClick para que no oculte el diálogo
        }
    }

}