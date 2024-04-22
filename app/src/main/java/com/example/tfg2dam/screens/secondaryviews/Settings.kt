package com.example.tfg2dam.screens.secondaryviews

import android.media.audiofx.BassBoost.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.settingsheader.SettingsHeader
import com.example.tfg2dam.settingsscreen.SettingsScreen

@Composable
fun Settings(navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Box {
            SettingsHeader(
                onSettingsTitleClicked = { navController.navigate("Home") },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box{
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SettingsScreen()
            }
        }
    }
}

// Arreglar problemas de posicionamiento