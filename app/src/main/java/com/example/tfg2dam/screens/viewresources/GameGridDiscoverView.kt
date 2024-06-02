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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.viewmodel.VideojuegosViewModel

/**
 * Vista de contenido en forma de cuadrícula para la pantalla de busqueda.
 * @param navController Controlador de navegación para navegar a los detalles del juego.
 * @param viewModel ViewModel para manejar los datos de los videojuegos.
 * @param pad Espacios de relleno para la vista.
 */
@Composable
fun ContenidoGridDiscoverView(
    navController: NavController,
    viewModel: VideojuegosViewModel,
    pad: PaddingValues
) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasSearched by viewModel.hasSearched.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(pad).background(Color(android.graphics.Color.parseColor("#141414"))),) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val nonNullResults = searchResults
            if (hasSearched && nonNullResults.isEmpty()) {
                Text(
                    text = "No se encontraron resultados de búsqueda",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(nonNullResults.size) { index ->
                        CardJuegoDiscoverView(juego = nonNullResults[index], navController = navController)
                    }
                }
            }
        }
    }
}

/**
 * Tarjeta que muestra la vista de un juego en la pantalla de busqueda.
 * @param juego Información del juego a mostrar.
 * @param navController Controlador de navegación para navegar a los detalles del juego.
 */
@Composable
fun CardJuegoDiscoverView(juego: VideojuegosLista, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                Log.i("Id enviado", "${juego.id}")
                navController.navigate("GameDetailsScreen/${juego.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
        ) {
            // Comprueba si la imagen no es null y no está vacía
            val imageUrl = juego.image ?: "" // Aunque el IDE diga que no es necesario, sin este operador elvis, la aplicación da un problema crítico de null pointer
            if (imageUrl.isNotEmpty()) {
                GameImageDiscoverView(imagen = imageUrl)
            } else {
                GameImageDiscoverView(imagen = "")
            }

            // Comprueba si el nombre no es null
            val gameName = juego.name ?: "Nombre no disponible" // Aunque el IDE diga que no es necesario, sin este operador elvis, la aplicación da un problema crítico de null pointer
            Text(
                text = gameName,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}

/**
 * Vista de la imagen del juego en la pantalla de busqueda.
 * @param imagen URL de la imagen del juego.
 */
@Composable
fun GameImageDiscoverView(imagen: String) {
    if (imagen.isNotEmpty()) {
        Image(
            painter = rememberAsyncImagePainter(imagen),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
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