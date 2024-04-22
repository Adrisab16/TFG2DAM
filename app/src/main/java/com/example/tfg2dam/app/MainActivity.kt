package com.example.tfg2dam.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
//import com.example.tfg2dam.MainScreen
import com.example.tfg2dam.MyScreen
import com.example.tfg2dam.ui.theme.TFG2DAMTheme
import com.example.tfg2dam.viewmodel.VideojuegosViewModel
//import com.example.tfg2dam.viewmodel.VideogamesViewModel
import com.example.tfg2dam.viewmodel.loginViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val loginVM : loginViewModel by viewModels()
        val gameVM : VideojuegosViewModel by viewModels()
        setContent {
            TFG2DAMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //NavManager(loginVM, gameVM) // Activar luego de termianr el test
                    //MainScreen(VideogamesViewModel())
                    MyScreen(gameVM)
                }
            }
        }
    }
}