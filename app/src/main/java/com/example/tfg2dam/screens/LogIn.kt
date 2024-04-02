package com.example.tfg2dam.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tfg2dam.R
import com.example.tfg2dam.emailfield.EmailField
import com.example.tfg2dam.loginfield.LogInField
import com.example.tfg2dam.loginheader.LoginHeader
import com.example.tfg2dam.loginheader.Property1
import com.example.tfg2dam.passwordfield.PasswordField
import com.example.tfg2dam.platformsicons.PlatformsIcons


@Composable
fun LogIn() {
    var emailText by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background_login_background_photo), // Reemplaza "your_image" con el nombre de tu imagen en los recursos
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Esto ajustará la escala de contenido para que la imagen ocupe todo el espacio disponible sin recortarla
        )

        // Titulo de la pantalla de LogIn
        LoginHeader(property1 = Property1.Default, modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp))

        Box(modifier = Modifier.align(Alignment.Center)) {

            LogInField()

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
                    EmailField()
                    Spacer(modifier = Modifier.height(50.dp)) // Agrega un espacio entre los campos
                    PasswordField()
                }
            }
        }
    }
}