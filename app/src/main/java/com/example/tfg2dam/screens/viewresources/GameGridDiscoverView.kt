package com.example.tfg2dam.screens.viewresources

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.viewmodel.VideojuegosViewModel

// Este funciona:
@Composable
fun ContenidoGridDiscoverView(
    navController: NavController,
    viewModel: VideojuegosViewModel,
    pad: PaddingValues
) {
    val searchResults by viewModel.searchResults.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .padding(pad)
            .background(Color(android.graphics.Color.parseColor("#141414"))),
    ) {
        items(searchResults) {
            CardJuegoDiscoverView(navController = navController, juego = it)
        }
    }
}

@Composable
fun CardJuegoDiscoverView(juego: VideojuegosLista, navController: NavController) {
    val searched by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                Log.i("Id enviado", "${juego.id}")
                navController.navigate("GameDetailsScreen/${juego.id}/$searched")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (!juego.image.isNullOrEmpty()) {
                GameImageDiscoverView(imagen = juego.image)
            } else {
                // Si la imagen es null, mostrar el marcador de posición
                GameImageDiscoverView(imagen = "")
            }
            Text(
                text = juego.name,
                modifier = Modifier.padding(8.dp),
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}

@Composable
fun GameImageDiscoverView(imagen: String) {
    if (imagen.isNotEmpty()) {
        // Asumiendo que estás usando Coil o alguna otra biblioteca de carga de imágenes
        Image(
            painter = rememberImagePainter(imagen),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
    } else {
        // Proporcionar un marcador de posición
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(androidx.compose.ui.graphics.Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}