package com.example.tfg2dam.screens.viewresources

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val clickedState = remember { mutableStateOf(false) }
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

        Column(modifier = Modifier
            .background(Color.Red)
            .fillMaxSize()
            .height(150.dp)
            .size(20.dp)
            .clickable {
                clickedState.value = true
            },
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
    if(clickedState.value){
        removeGameFromUser(userVideogameVM, userId, gameId, gametype)
        when(gametype){
            "CP" -> {nameGameType = "Currenty Playing"}
            "DR" -> {nameGameType = "Dropped"}
            "OH" -> {nameGameType = "On-Hold"}
            "CTD" -> {nameGameType = "Completed"}
            "PTP" -> {nameGameType = "Plan to Play"}
        }
        Toast.makeText(
            LocalContext.current,
            "${juego.name} ha sido eliminado de $nameGameType",
            Toast.LENGTH_SHORT
        ).show()
        navController.navigate("MyList")
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
        userVideogameVM.removeGameIdFromUser(userId, gameId, gametype)
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
