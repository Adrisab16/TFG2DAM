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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.menudesplegable.MenuDesplegable
import com.example.tfg2dam.mylisttopnavbar.MyListTopNavBar
import com.example.tfg2dam.screens.viewresources.ChangePasswordDialog
import com.example.tfg2dam.screens.viewresources.ContenidoListView
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel
import com.example.tfg2dam.viewmodel.userVideogameViewModel
import kotlinx.coroutines.launch


@Composable
fun MyList(navController: NavController, loginVM: loginViewModel, userVideoGameVM: userVideogameViewModel, countlistout: String) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var countlist by remember { mutableIntStateOf(countlistout.toInt()) }
    var userId by remember { mutableStateOf("") } // Estado para almacenar el ID de usuario
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loginVM.getUserIdFromFirestore { retrievedUserId ->
            retrievedUserId.let {
                userId = it
                Log.i("USERID", userId)
            }
        }

        loginVM.getUsernameFromFirestore { retrievedUsername ->
            retrievedUsername?.let {
                username = it
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#141414")))
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            // Header
            Header(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onUserIconClicked = { isMenuVisible = true },
            )

            // Top Nav Bar
            MyListTopNavBar(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onPlayingButtonClicked = { countlist = 1 },
                onDroppedButtonClicked = { countlist = 2 },
                onOnHoldButtonClicked = { countlist = 3 },
                onCompletedButtonClicked = { countlist = 4 },
                onPlanToPlayButtonClicked = { countlist = 5 },
            )

            // List view with filtered games
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                when (countlist) {
                    // Default
                    0 -> {
                        Text(text = "Elige una lista", style = TextStyle(color = Color.White), modifier= Modifier.align(
                            Alignment.Center))
                    }

                    // Lista Currently Playing
                    1 -> {
                        ContenidoListView(
                            navController = navController,
                            gametype = "CP",
                            pad = PaddingValues(10.dp),
                            userVideogameVM = userVideoGameVM,
                            viewModel = VideojuegosViewModel(),
                            userId = userId,
                        )
                    }

                    // Lista Dropped
                    2 -> {
                        ContenidoListView(
                            navController = navController,
                            gametype = "DR",
                            pad = PaddingValues(10.dp),
                            userVideogameVM = userVideoGameVM,
                            viewModel = VideojuegosViewModel(),
                            userId = userId // Pasar el userId aquí
                        )
                    }

                    // Lista On-Hold
                    3 -> {
                        ContenidoListView(
                            navController = navController,
                            gametype = "OH",
                            pad = PaddingValues(10.dp),
                            userVideogameVM = userVideoGameVM,
                            viewModel = VideojuegosViewModel(),
                            userId = userId // Pasar el userId aquí
                        )
                    }

                    // Lista Completed
                    4 -> {
                        ContenidoListView(
                            navController = navController,
                            gametype = "CTD",
                            pad = PaddingValues(10.dp),
                            userVideogameVM = userVideoGameVM,
                            viewModel = VideojuegosViewModel(),
                            userId = userId // Pasar el userId aquí
                        )
                    }

                    // Lista Plan to Play
                    5 -> {
                        ContenidoListView(
                            navController = navController,
                            gametype = "PTP",
                            pad = PaddingValues(10.dp),
                            userVideogameVM = userVideoGameVM,
                            viewModel = VideojuegosViewModel(),
                            userId = userId // Pasar el userId aquí
                        )
                    }
                }
            }

            // Footer Nav Tab
            FooterNavTab(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                property1 = Property1.MyListClicked,
                onHomeButtonClicked = { navController.navigate("Home") },
                onDiscoverButtonClicked = { navController.navigate("Discover") },
            )
        }

        // Animated visibility for the menu
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
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { },
                    onLogOutButtonBackgroundClicked = { loginVM.logout(); navController.navigate("Login") },
                    usernameTxttextcontent = "Hola, $username",
                    onDeleteButtonClicked = { showDeleteDialog = true },
                    onChangePasswordClicked = { showChangePasswordDialog = true },
                    onCompletedListClicked = { navController.navigate("MyList/4") },
                    onDroppedClicked = { navController.navigate("MyList/2") },
                    onOnHoldListDailyChallengesClicked = { navController.navigate("MyList/3") },
                    onPlanToPlayMyStadisticsClicked = { navController.navigate("MyList/5") },
                    onPlayingListClicked = { navController.navigate("MyList/1") },
                )
            }
        }

        // Dialogs
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