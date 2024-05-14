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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.tfg2dam.screens.viewresources.ContenidoGridView
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel


@Composable
fun Discover(navController: NavController, loginVM: loginViewModel, gameVM: VideojuegosViewModel){
    var isMenuVisible by remember { mutableStateOf(false) }
    val isloaded by remember { mutableStateOf(true) }
    //val games by gameVM.games.collectAsState()
    var username by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


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
        //Header:

        if(isloaded){
            // Grid de videojuegos:
            Box(modifier = Modifier
                .padding(top = 80.dp, bottom = 60.dp)){
                Scaffold {
                    ContenidoGridView(
                        viewModel = gameVM,
                        pad = it,
                        navController = navController
                    )
                }
            }
        }
        else{
            Box(modifier = Modifier
                .padding(top = 80.dp, bottom = 60.dp)){
                CircularProgressIndicator()
            }
        }

        Header(
            modifier = Modifier
                .padding(bottom = 700.dp)
                .align(Alignment.Center)
            , // Agregar espacio superior
            onUserIconClicked = { isMenuVisible = true },
        )

        // Footer:
        FooterNavTab(modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.DiscoverClicked,
            onHomeButtonClicked = {navController.navigate("Home")},
            onListButtonClicked = {navController.navigate("MyList/0")},
            )

        // El codigo respectivo para la visualización del menu desplegable:
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
                    usernameTxttextcontent = "Hola, $username",
                    onDeleteButtonClicked = { showDialog = true },
                    onCompletedListClicked = {
                        val countlistout = 4
                        navController.navigate("MyList/$countlistout")
                    },
                    onDroppedClicked = {
                        val countlistout = 2
                        navController.navigate("MyList/$countlistout")
                    },
                    onOnHoldListDailyChallengesClicked = {
                        val countlistout = 3
                        navController.navigate("MyList/$countlistout")
                    },
                    onPlanToPlayMyStadisticsClicked = {
                        val countlistout = 5
                        navController.navigate("MyList/$countlistout")
                    },
                    onPlayingListClicked = {
                        val countlistout = 1
                        navController.navigate("MyList/$countlistout")
                    },
                )
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Eliminar cuenta") },
                text = { Text("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.") },
                confirmButton = {
                    Button(
                        onClick = {
                            loginVM.deleteAccount {
                                navController.navigate("Login")
                            }
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}