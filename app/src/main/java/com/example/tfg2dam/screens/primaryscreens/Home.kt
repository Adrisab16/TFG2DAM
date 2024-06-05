package com.example.tfg2dam.screens.primaryscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
    val context = LocalContext.current
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#141414")))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onUserIconClicked = { isMenuVisible = true },
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TOP RATING GAMES",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Serif,
                        color = Color.White
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f),
                contentAlignment = Alignment.Center
            ) {
                Scaffold {
                    ContenidoGridView(
                        viewModel = gameVM,
                        pad = it,
                        navController = navController
                    )
                }
            }

            FooterNavTab(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                property1 = Property1.HomeClicked,
                onListButtonClicked = { navController.navigate("MyList/0") },
                onDiscoverButtonClicked = { navController.navigate("Discover") },
            )
        }

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = 300)),
            exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis = 300))
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000))
                    .clickable { isMenuVisible = false }
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .clickable {  }
                ) {
                    MenuDesplegable(
                        modifier = Modifier.clickable { },
                        onLogOutButtonBackgroundClicked = { loginVM.logout(); navController.navigate("Login") },
                        usernameTxttextcontent = "Hola, $username",
                        onDeleteButtonClicked = { showDeleteDialog = true },
                        onChangePasswordClicked = { showChangePasswordDialog = true },
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
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar cuenta") },
                text = { Text("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.") },
                confirmButton = {
                    Button(
                        onClick = {
                            loginVM.deleteAccount(context) {
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

        val coroutineScope = rememberCoroutineScope()

        if (showChangePasswordDialog) {
            val context = LocalContext.current
            ChangePasswordDialog(
                onDismiss = { showChangePasswordDialog = false },
                onConfirm = { oldPassword, newPassword ->
                    loginVM.changePassword(oldPassword, newPassword,
                        onError = { errorMessage ->
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