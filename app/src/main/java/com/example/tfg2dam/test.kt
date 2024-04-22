package com.example.tfg2dam

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.model.VideojuegosLista
import com.example.tfg2dam.viewmodel.VideojuegosViewModel

@Composable
fun MyScreen(viewModel: VideojuegosViewModel) {
    Scaffold {
        ContenidoInicioView(
            viewModel = viewModel,
            pad = it
        )
    }
}

@Composable
fun ContenidoInicioView(
    viewModel: VideojuegosViewModel,
    pad: PaddingValues
){
    val juegos by viewModel.juegos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(pad)
            .background(Color.Red),
    ){
        items(juegos) {
            CardJuego(juego = it) {  }
            Text(
                text = it.name,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier =  Modifier
                    .padding(start = 12.dp)
            )
        }
    }
}

@Composable
fun CardJuego(
    juego: VideojuegosLista,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .shadow(40.dp)
            .clickable { onClick() }
    ){
        Column {
            InicioImagen(imagen = juego.image)
        }
    }
}

@Composable
fun InicioImagen(imagen: String){
    val imagen = rememberAsyncImagePainter(model = imagen)

    Image (
        painter = imagen,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}

