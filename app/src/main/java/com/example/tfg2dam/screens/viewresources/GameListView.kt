package com.example.tfg2dam.screens.viewresources

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.userVideogameViewModel
import kotlinx.coroutines.launch


// Este funciona:
@Composable
fun ContenidoListView(
    navController: NavController,
    viewModel: VideojuegosViewModel,
    userVideogameVM: userVideogameViewModel,
    pad: PaddingValues,
    gametype: String,
    userId: String,
) {
    val juegos by viewModel.juegos.collectAsState()
    var filteredJuegos by remember { mutableStateOf<List<VideojuegosLista>>(emptyList()) }

    // Llamada a LaunchedEffect solo para actualizar la lista de juegos filtrados
    LaunchedEffect(juegos) {
        val gameIds = userVideogameVM.getVideoGamesByType(gameType = gametype, userId = userId)
        filteredJuegos = juegos.filter { it.id in gameIds.orEmpty() }
    }

    LazyColumn(
        modifier = Modifier
            .padding(pad)
            .background(Color(android.graphics.Color.parseColor("#141414"))),
        verticalArrangement = Arrangement.Top
    ) {
        items(filteredJuegos) { juego ->
            CardJuegoListView(
                navController = navController,
                juego = juego,
                gametype = gametype,
                userVideogameVM = userVideogameVM,
                userId = userId,
                gameId = juego.id
            )
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardJuegoListView(
    navController: NavController,
    juego: VideojuegosLista,
    gametype: String,
    gameId: Int,
    userId: String,
    userVideogameVM: userVideogameViewModel,
) {
    // Mantén un estado para controlar si se ha hecho clic en el botón de eliminación
    var clickedState by remember { mutableStateOf(false) }
    var nameGameType by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .shadow(40.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
            .clickable {
                navController.navigate("GameDetailsScreen/${juego.id}")
            }
    ){
        GameImageListView(imagen = juego.image)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Text(text = juego.name, color = Color.Black, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Nota Metacritic: ${juego.mcscore}/100", color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Tiempo de juego: ${juego.gameplaytime}h",color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Fecha salida: ${juego.datereleased}",color = Color.Black)
        }
        Spacer(modifier = Modifier.width(10.dp))
        // Verifica si se ha hecho clic en el botón de eliminación
        Column(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxSize()
                .height(150.dp)
                .size(20.dp)
                .clickable {
                    clickedState = true
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = null,
                tint = Color.White
            )
        }
    }

    val coroutineScope = rememberCoroutineScope()
    var countlistout by remember { mutableIntStateOf(0) }
    if (clickedState) {
        coroutineScope.launch {
            val success = userVideogameVM.removeGameIdFromUser(userId, gameId, gametype)
            if (success) {
                when(gametype){
                    "CP" -> {nameGameType = "Currently Playing"; countlistout = 1}
                    "DR" -> {nameGameType = "Dropped"; countlistout = 2}
                    "OH" -> {nameGameType = "On-Hold"; countlistout = 3}
                    "CTD" -> {nameGameType = "Completed"; countlistout = 4}
                    "PTP" -> {nameGameType = "Plan to Play"; countlistout = 5}
                }
                navController.navigate("MyList/$countlistout")
                // Restablecer el estado de clickedState
                clickedState = false
            }
        }
        Toast.makeText(
            LocalContext.current,
            "${juego.name} has been removed from $nameGameType",
            Toast.LENGTH_SHORT
        ).show()
    }
}



@Composable
fun removeGameFromUser(
    userVideogameVM: userVideogameViewModel,
    userId: String,
    gameId: Int,
    gametype: String
) {
    // Lógica para eliminar el juego del usuario
    LaunchedEffect(Unit) {
        try {
            userVideogameVM.removeGameIdFromUser(userId, gameId, gametype)
        } catch (e: Exception) {
            // Manejar la excepción aquí
            Log.e("ERROR AL ELIMINAR JUEGO", "Error al eliminar el juego: ${e.localizedMessage}", e)
            // Puedes mostrar un mensaje de error si lo deseas
            // Toast.makeText(
            //     LocalContext.current,
            //     "Error al eliminar el juego: ${e.localizedMessage}",
            //     Toast.LENGTH_SHORT
            // ).show()
        }
    }
}


@Composable
fun GameImageListView(imagen: String){
    val imagenPainter = rememberAsyncImagePainter(model = imagen)

    Image (
        painter = imagenPainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(100.dp)
            .height(150.dp)
    )
}
