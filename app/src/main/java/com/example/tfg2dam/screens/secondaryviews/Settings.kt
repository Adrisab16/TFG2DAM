package com.example.tfg2dam.screens.secondaryviews


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tfg2dam.settingsheader.SettingsHeader
import com.example.tfg2dam.settingsscreen.SettingsScreen
import com.example.tfg2dam.viewmodel.loginViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Settings(navController: NavController, loginVM:loginViewModel) {
    Box(modifier= Modifier.fillMaxSize().background(Color(android.graphics.Color.parseColor("#141414")))) {

        //Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.align(Alignment.Center).padding(top = 5.0.dp)){
            Column {
                SettingsScreen(
                    onDeleteAccountButtonClicked = { loginVM.deleteAccount { loginVM.logout(); navController.navigate("Login")/* Añadir un showAlert para confirmar la eliminacion de la cuenta*/ } }
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            SettingsHeader(
                onSettingsTitleClicked = { navController.navigate("Home") },
            )
        }
    }
}

// Arreglar problemas de posicionamiento