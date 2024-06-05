package com.example.tfg2dam.screens.login_signupscreens

import android.widget.Toast
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

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background_login_background_photo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            LoginHeader(
                property1 = Property1.Default,
                modifier = Modifier.padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LogInField()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Box{
                        PlatformsIcons(
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))


                    Box(
                        modifier = Modifier
                            .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        EmailField(
                            textFieldValue = loginVM.email,
                            onTextFieldValueChanged = { loginVM.cambioDeEmail(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Box(
                        modifier = Modifier
                            .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        PasswordField(
                            textFieldValue = loginVM.password,
                            onTextFieldValueChanged = { loginVM.cambioDeContrasenia(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    LogInButton(
                        onLogInButtonClicked = {
                            loginVM.login(
                                onSuccess = { navController.navigate("Home") },
                                onError = { errorMessage ->
                                    coroutineScope.launch {
                                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    NavSignUpButton(
                        onNavButtonSignUpClicked = { navController.navigate("SignUp") },
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )
                }
            }
        }
    }
}