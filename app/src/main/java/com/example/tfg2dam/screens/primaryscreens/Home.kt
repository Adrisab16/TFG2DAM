package com.example.tfg2dam.screens.primaryscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.menudesplegable.MenuDesplegable
import com.example.tfg2dam.screens.viewresources.ChangePasswordDialog
import com.example.tfg2dam.screens.viewresources.ContenidoGridView
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel
import kotlinx.coroutines.launch


@Composable
fun Home(navController: NavController, loginVM: loginViewModel, gameVM: VideojuegosViewModel) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        loginVM.getUsernameFromFirestore { retrievedUsername ->
            retrievedUsername?.let {
                username = it
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(android.graphics.Color.parseColor("#141414")))) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "TOP RATING GAMES",
                style = TextStyle(
                    fontWeight = FontWeight.Bold, // Negrita
                    fontSize = 24.sp, // Tamaño grande
                    fontFamily = FontFamily.Serif, // Fuente bonita (puedes cambiar a la que desees)
                    color = Color.White
                ),
            )
        }


        Box(modifier = Modifier
            .padding(top = 160.dp, bottom = 60.dp)
            .background(Color(android.graphics.Color.parseColor("#141414")))
        ){
            Scaffold {
                ContenidoGridView(
                    viewModel = gameVM,
                    pad = it,
                    navController = navController
                )
            }
        }

        Header(
            modifier = Modifier
                .padding(bottom = 700.dp)
                .align(Alignment.Center)
            , // Agregar espacio superior
            onUserIconClicked = { isMenuVisible = true },
        )

        FooterNavTab(
            modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.HomeClicked,
            onListButtonClicked = { navController.navigate("MyList/0") },
            onDiscoverButtonClicked = { navController.navigate("Discover") },
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
                    usernameTxttextcontent = "Hola, $username",
                    onDeleteButtonClicked = { showDeleteDialog = true },
                    onChangePasswordClicked = {showChangePasswordDialog = true},
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
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
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
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        val coroutineScope = rememberCoroutineScope() // Obtener el ámbito de la coroutine

        if (showChangePasswordDialog) {
            val context = LocalContext.current
            ChangePasswordDialog(
                onDismiss = { showChangePasswordDialog = false },
                onConfirm = { oldPassword, newPassword ->
                    loginVM.changePassword(oldPassword, newPassword,
                        onError = { errorMessage ->
                            // Ejecutar la llamada al Toast dentro de una coroutine
                            coroutineScope.launch {
                                Toast.makeText(context, "Contraseña antigua incorrecta", Toast.LENGTH_LONG).show()
                            }
                            Log.e("ChangePassword", errorMessage)
                        },
                        navController = navController
                    )
                }
            )
        }
    }
}
