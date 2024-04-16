package com.example.tfg2dam.screens.primaryscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.menudesplegable.MenuDesplegable
import com.example.tfg2dam.network.RetrofitClient.api
import com.example.tfg2dam.viewmodel.loginViewModel
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberImagePainter


@Composable
fun Discover(navController: NavController, loginVM: loginViewModel){
    var isMenuVisible by remember { mutableStateOf(false) }


    Box(Modifier.fillMaxSize()) {
        //Header:
        Header(
            modifier = Modifier
                .padding(bottom = 700.dp)
                .align(Alignment.Center)
            , // Agregar espacio superior
            onUserIconClicked = { isMenuVisible = true },
        )

        // var games by remember { mutableStateOf<List<GameModel>>(emptyList()) }

        // Llama a la API para obtener los juegos:

        /*LaunchedEffect(Unit) {
            try {
                val response = api.getGames(page = 1, pageSize = 20)
                games = response.results
            } catch (e: Exception) {
                // Manejo de errores
            }
        }*/

        // Lo muestra en un VerticalGrid (un listado de imagenes junto a su texto):

        /*LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier.fillMaxSize(),
            ) {
            items(games) { game ->
                GameItem(game = game)
            }
        }*/
        Text("Hola")

        // Footer:
        FooterNavTab(modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.DiscoverClicked,
            onHomeButtonClicked = {navController.navigate("Home")},
            onListButtonClicked = {navController.navigate("MyList")},
            )

        // El codigo respectivo para la visualización del menu desplegable:
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
                    modifier = Modifier.clickable {  },
                    onLogOutButtonBackgroundClicked = { loginVM.logout(); navController.navigate("Login") },
                    onSettingsButtonClicked = {navController.navigate("Settings")},
                    usernameTxttextcontent = "Hola,"
                )
            }
        }
    }
}

/*
@Composable
fun GameItem(game: GameModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        // Imagen del juego
        Image(
            painter = rememberImagePainter(game.imageUrl),
            contentDescription = game.name,
            modifier = Modifier.size(72.dp, 98.dp),
            contentScale = ContentScale.Crop
        )
        // Nombre del juego
        Text(
            text = game.name,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
*/