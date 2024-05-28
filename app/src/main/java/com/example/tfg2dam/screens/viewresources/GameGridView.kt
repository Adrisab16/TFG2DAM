package com.example.tfg2dam.screens.viewresources

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.viewmodel.VideojuegosViewModel


// Este funciona:
@Composable
fun ContenidoGridView(
    navController: NavController,
    viewModel: VideojuegosViewModel,
    pad: PaddingValues
) {
    val juegos by viewModel.juegos.collectAsState()

    if (juegos.isEmpty()) {
        // Mostrar CircularProgressIndicator si la lista de juegos está vacía
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Blue)
        }
    } else {
        val primeros40Juegos = juegos.take(40) // Tomar solo los primeros 40 juegos

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(pad)
                .background(Color(android.graphics.Color.parseColor("#141414"))),
        ) {
            items(primeros40Juegos) {
                CardJuego(navController = navController, juego = it)
            }
        }
    }
}

@Composable
fun CardJuego(
    navController: NavController,
    juego: VideojuegosLista,
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .shadow(40.dp)
            .clickable {
                // Cuando se hace clic en el juego, navegamos a GameDetailsScreen pasando el ID del juego
                navController.navigate("GameDetailsScreen/${juego.id}")
            },
    ) {
        Column(modifier = Modifier.background(Color.DarkGray)) {
            GameImage(imagen = juego.image)
            Text(
                text = juego.name,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Composable
fun GameImage(imagen: String) {
    val imagenPainter = rememberAsyncImagePainter(model = imagen)

    Image(
        painter = imagenPainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}