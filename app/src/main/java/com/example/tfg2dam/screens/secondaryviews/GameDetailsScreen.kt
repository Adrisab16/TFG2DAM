package com.example.tfg2dam.screens.secondaryviews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tfg2dam.footernavtab.FooterNavTab
import com.example.tfg2dam.footernavtab.Property1
import com.example.tfg2dam.gamedetailsfield.GameDetailsField
import com.example.tfg2dam.header.Header
import com.example.tfg2dam.menudesplegable.MenuDesplegable
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel

@Composable
fun GameDetailsScreen(navController: NavHostController, loginVM: loginViewModel, id: Int, gameVM: VideojuegosViewModel) {
    var isMenuVisible by remember { mutableStateOf(false) }
    val gameName = gameVM.getGameNameById(id)
    val gameImage = gameVM.getGameImageById(id)
    val gameImagePainter: Painter = rememberAsyncImagePainter(model = gameImage, contentScale = ContentScale.Crop)
    val gameMetacriticScore = gameVM.getGameMcScoreById(id)
    val gamePlaytime = gameVM.getGamePlayTimeById(id)
    val gameDateReleased = gameVM.getGameDateById(id)


    // Para esta pantalla, me gustaria extraer el tiempo de juego (playtime) y el rating de metacritic


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(android.graphics.Color.parseColor("#141414")))) {
        Header(
            modifier = Modifier
                .padding(bottom = 700.dp)
                .align(Alignment.Center)
            , // Agregar espacio superior
            onUserIconClicked = { isMenuVisible = true },
        )

        Box(modifier = Modifier.padding(top = 100.dp, start = 40.dp)){
            GameDetailsField(
                titletxtextcontent = gameName,
                gamePhotoimagecontent = gameImagePainter,
                metacriticScoretextcontent = "Metacritic Score: $gameMetacriticScore/100",
                gameHourstextcontent = "Hours to beat:\n$gamePlaytime horas",
                releasedtextcontent = "Fecha de Lanzamiento: $gameDateReleased",
            ){
            }
        }

        FooterNavTab(
            modifier = Modifier.align(Alignment.BottomCenter),
            property1 = Property1.DiscoverClicked,
            onListButtonClicked = { navController.navigate("MyList") },
            onDiscoverButtonClicked = { navController.navigate("Discover") },
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
                    modifier = Modifier.clickable {  },
                    onLogOutButtonBackgroundClicked = { loginVM.logout(); navController.navigate("Login") },
                    onSettingsButtonClicked = {navController.navigate("Settings")},
                    usernameTxttextcontent = "Hola,"
                )
            }
        }
    }
}