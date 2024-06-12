package com.example.tfg2dam.screens.secondaryviews

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.gamedetailsfield.GameDetailsField
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.menudesplegable.MenuDesplegable
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.screens.viewresources.ChangePasswordDialog
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.LoginViewModel
import com.example.tfg2dam.viewmodel.UserVideogameViewModel
import kotlinx.coroutines.launch

@Composable
fun GameDetailsScreen(
    navController: NavHostController,
    loginVM: LoginViewModel,
    userVideogameVM: UserVideogameViewModel,
    id: Int,
    gameVM: VideojuegosViewModel,
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var infojuego by remember { mutableStateOf<List<VideojuegosLista>>(emptyList()) }
    var username by remember { mutableStateOf("") }
    var userid by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var selectedList by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showAddGameDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.i("Id recibido en GameDetailsScreen", "$id")

        loginVM.getUsernameFromFirestore { retrievedUsername ->
            retrievedUsername?.let {
                username = it
            }
        }
        loginVM.getUserIdFromFirestore { userId ->
            userid = userId
        }
        gameVM.obtenerJuegoPorId(id) { juegosObtenidos ->
            infojuego = juegosObtenidos
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#141414")))
    ) {
        Header(
            modifier = Modifier
                .padding(bottom = 700.dp)
                .align(Alignment.Center),
            onUserIconClicked = { isMenuVisible = true },
            onAppTitleClicked = { navController.navigate("Home") }
        )

        if (infojuego.isNotEmpty()) {
            Box(modifier = Modifier.padding(top = 100.dp, start = 40.dp)) {
                val gameImagePainter: Painter = rememberAsyncImagePainter(model = infojuego[0].image, contentScale = ContentScale.Crop)
                GameDetailsField(
                    titletxtextcontent = infojuego[0].name,
                    gamePhotoimagecontent = gameImagePainter,
                    metacriticScoretextcontent = "Metacritic Score: ${infojuego[0].mcscore}/100",
                    gameHourstextcontent = "Hours to beat:\n${infojuego[0].gameplaytime} horas",
                    releasedtextcontent = "Fecha de Lanzamiento: ${infojuego[0].datereleased}",
                    onAddButtonClicked = { showAddGameDialog = true }
                )

                if (showAddGameDialog) {
                    AlertDialog(
                        onDismissRequest = { showAddGameDialog = false },
                        title = { Text("Añadir:\n${infojuego[0].name}") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = rating,
                                    onValueChange = { rating = it },
                                    label = { Text("Valoración (0-10)") },
                                    singleLine = true
                                )
                                var expanded by remember { mutableStateOf(false) }
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 5.dp, top = 10.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .background(Color(android.graphics.Color.parseColor("#141414")))
                                        .clickable { expanded = true }
                                ) {
                                    Text(
                                        text = selectedList.ifEmpty { "Seleccionar lista" },
                                        modifier = Modifier
                                            .padding(bottom = 10.dp, top = 10.dp, start = 30.dp, end = 30.dp)
                                    )
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        listOf("Currently Playing", "Plan to Play", "Dropped", "On-Hold", "Completed").forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option) },
                                                onClick = {
                                                    selectedList = option
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    // Validar y añadir el juego a la lista del usuario
                                    val ratingValue = rating.toIntOrNull()
                                    if (ratingValue != null && ratingValue in 0..10 && selectedList.isNotEmpty()) {
                                        scope.launch {
                                            when(selectedList){
                                                "Currently Playing"->{userVideogameVM.addGameIdToUser(userid, id, ratingValue, "CP")}
                                                "Plan to Play"->{userVideogameVM.addGameIdToUser(userid, id, ratingValue, "PTP")}
                                                "Dropped"->{userVideogameVM.addGameIdToUser(userid, id, ratingValue, "DR")}
                                                "On-Hold"->{userVideogameVM.addGameIdToUser(userid, id, ratingValue, "OH")}
                                                "Completed"->{userVideogameVM.addGameIdToUser(userid, id, ratingValue, "CTD")}
                                            }

                                            showToast("Juego añadido a la lista $selectedList", context)
                                            showAddGameDialog = false
                                        }
                                    } else {
                                        showToast("Por favor, introduce una valoración válida y selecciona una lista.", context)
                                    }
                                }
                            ) {
                                Text("Añadir")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showAddGameDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        } else {
            // Mostrar algún indicador de carga o un mensaje de que no hay datos
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }

        FooterNavTab(
            modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.DiscoverClicked,
            onListButtonClicked = { navController.navigate("MyList/0") },
            onDiscoverButtonClicked = { navController.navigate("Discover") },
            onHomeButtonClicked = { navController.navigate("Home") }
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
                    modifier = Modifier.clickable { },
                    onLogOutButtonBackgroundClicked = { loginVM.logout(); navController.navigate("Login") },
                    usernameTxttextcontent = "Hola,\n$username",
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
                    Button(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        val coroutineScope = rememberCoroutineScope() // Obtener el ámbito de la coroutine

        if (showChangePasswordDialog) {
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

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
