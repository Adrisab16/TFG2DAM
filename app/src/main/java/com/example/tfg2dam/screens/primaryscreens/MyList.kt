package com.example.tfg2dam.screens.primaryscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.menudesplegable.MenuDesplegable
import com.example.tfg2dam.mylisttopnavbar.MyListTopNavBar
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel
import com.example.tfg2dam.viewmodel.userVideogameViewModel

@Composable
fun MyList(navController: NavController, loginVM: loginViewModel, userVideoGameVM:userVideogameViewModel, videoGameVM:VideojuegosViewModel){
    var isMenuVisible by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loginVM.getUsernameFromFirestore { retrievedUsername ->
            retrievedUsername?.let {
                username = it
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#141414")))) {
        Header(
            modifier = Modifier
                .align(Alignment.TopCenter)
            , // Agregar espacio superior
            onUserIconClicked = { isMenuVisible = true },
        )

        MyListTopNavBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top=100.dp),
            onCompletedButtonClicked = {},
            onDroppedButtonClicked = {},
            onOnHoldButtonClicked = {},
            onPlanToPlayButtonClicked = {},
            onPlayingButtonClicked = {},
        )

        FooterNavTab(
            modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.MyListClicked,
            onHomeButtonClicked = {navController.navigate("Home")},
            onDiscoverButtonClicked = {navController.navigate("Discover")},
            )

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 300)),
            exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis = 300))
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000)) // Fondo semitransparente
                    .clickable { isMenuVisible = false } // Ocultar el menú al hacer clic fuera de él
            ) {
                MenuDesplegable(
                    modifier = Modifier.clickable {  },
                    onLogOutButtonBackgroundClicked = { loginVM.logout(); navController.navigate("Login") },
                    onSettingsButtonClicked = {navController.navigate("Settings")},
                    usernameTxttextcontent = "Hola,\n$username"
                )
            }
        }
    }
}