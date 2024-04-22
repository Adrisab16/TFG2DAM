package com.example.tfg2dam.screens.primaryscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.tfg2dam.screens.viewresources.ContenidoGridView
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
import com.example.tfg2dam.viewmodel.loginViewModel


@Composable
fun Discover(navController: NavController, loginVM: loginViewModel, gameVM: VideojuegosViewModel){
    var isMenuVisible by remember { mutableStateOf(false) }
    var isloaded by remember { mutableStateOf(true) }
    //val games by gameVM.games.collectAsState()


    Box(
        Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#141414")))) {
        //Header:

        if(isloaded){
            // Grid de videojuegos:
            Box(modifier = Modifier
                .padding(top = 80.dp, bottom = 60.dp)){
                Scaffold {
                    ContenidoGridView(
                        viewModel = gameVM,
                        pad = it,
                        navController = navController
                    )
                }
            }
        }
        else{
            Box(modifier = Modifier
                .padding(top = 80.dp, bottom = 60.dp)){
                CircularProgressIndicator()
            }
        }

        Header(
            modifier = Modifier
                .padding(bottom = 700.dp)
                .align(Alignment.Center)
            , // Agregar espacio superior
            onUserIconClicked = { isMenuVisible = true },
        )

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
fun GameItem(gameModel: GameModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        // Imagen del juego
        Image(
            painter = rememberAsyncImagePainter(gameModel.image), // Corrección aquí
            contentDescription = gameModel.name,
            modifier = Modifier.size(72.dp, 98.dp),
            contentScale = ContentScale.Crop
        )
        // Nombre del juego
        Text(
            text = gameModel.name,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}*/