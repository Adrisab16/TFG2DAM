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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.UserVideogameViewModel
import kotlinx.coroutines.launch


/**
 * Vista de contenido en forma de lista para mostrar juegos filtrados por tipo.
 * @param navController Controlador de navegación para navegar a los detalles del juego o a la lista de juegos filtrados.
 * @param viewModel ViewModel para manejar los datos de los videojuegos.
 * @param userVideogameVM ViewModel para manejar los datos de los videojuegos del usuario.
 * @param pad Espacios de relleno para la vista.
 * @param gametype Tipo de juego por el cual se filtrarán los juegos.
 * @param userId ID del usuario actual.
 */
@Composable
fun ContenidoListView(
    navController: NavController,
    viewModel: VideojuegosViewModel,
    userVideogameVM: UserVideogameViewModel,
    pad: PaddingValues,
    gametype: String,
    userId: String,
) {
    val juegos by viewModel.juegos.collectAsState()
    var isLoading by remember { mutableStateOf(true) }
    var filteredJuegos by remember { mutableStateOf<List<VideojuegosLista>>(emptyList()) }

    // Llamada a LaunchedEffect solo para actualizar la lista de juegos filtrados
    LaunchedEffect(juegos) {
        Log.i("COMBINED_JUEGOS", "Juegos combinados: $juegos")

        val gameIds = userVideogameVM.getVideoGamesByType(gameType = gametype, userId = userId)
        Log.i("GAMEIDS", "IDs de juegos: $gameIds")

        if (gameIds != null) {
            viewModel.obtenerJuegosPorIds(gameIds) { juegosObtenidos ->
                filteredJuegos = juegosObtenidos
                isLoading = false
            }
        } else {
            filteredJuegos = emptyList()
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (filteredJuegos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "La lista está vacía",
                    color = Color.White
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(pad)
            ) {
                items(filteredJuegos) { juego ->
                    CardJuegoListView(
                        navController = navController,
                        juego = juego,
                        gametype = gametype,
                        userVideogameVM = userVideogameVM,
                        userId = userId,
                        gameId = juego.id,
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta que muestra la vista de un juego en la lista.
 * @param navController Controlador de navegación para navegar a los detalles del juego o a la lista de juegos filtrados.
 * @param juego Información del juego a mostrar.
 * @param gametype Tipo de juego por el cual se filtrarán los juegos.
 * @param gameId ID del juego.
 * @param userId ID del usuario actual.
 * @param userVideogameVM ViewModel para manejar los datos de los videojuegos del usuario.
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardJuegoListView(
    navController: NavController,
    juego: VideojuegosLista,
    gametype: String,
    gameId: Int,
    userId: String,
    userVideogameVM: UserVideogameViewModel,
) {
    var clickedState by remember { mutableStateOf(false) }
    var nameGameType by remember { mutableStateOf("") }
    var valoracion by remember { mutableStateOf<Int?>(null) }  // Estado para la valoración

    // Obtener la valoración del juego desde Firebase
    LaunchedEffect(gameId) {
        valoracion = userVideogameVM.getValoracion(userId, gameId, gametype)
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable {
                navController.navigate("GameDetailsScreen/${juego.id}")
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
        ) {
            GameImageListView(imagen = juego.image)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = juego.name,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Clip
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Nota Metacritic: ${juego.mcscore}/100", color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Tiempo de juego: ${juego.gameplaytime}h", color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Valoración del usuario: ${valoracion ?: "0"}",  // Mostrar la valoración obtenida o "0" si es nula
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
                .background(Color.Red)
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
                when (gametype) {
                    "CP" -> { nameGameType = "Currently Playing"; countlistout = 1 }
                    "DR" -> { nameGameType = "Dropped"; countlistout = 2 }
                    "OH" -> { nameGameType = "On-Hold"; countlistout = 3 }
                    "CTD" -> { nameGameType = "Completed"; countlistout = 4 }
                    "PTP" -> { nameGameType = "Plan to Play"; countlistout = 5 }
                }
                navController.navigate("MyList/$countlistout")
                clickedState = false
            }
        }
        Toast.makeText(
            LocalContext.current,
            "${juego.name} ha sido eliminado",
            Toast.LENGTH_SHORT
        ).show()
    }
}

/**
 * Composable function to display a game image in a list.
 * @param imagen The URL of the image to display.
 */
@Composable
fun GameImageListView(imagen: String) {
    val imagenPainter = rememberAsyncImagePainter(model = imagen)

    Image(
        painter = imagenPainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(100.dp)
            .fillMaxHeight() // Ajustar la altura de la imagen a la altura del contenedor padre
    )
}