package com.example.tfg2dam.uiresources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Función composable que muestra un Textfield de usuario con un icono de persona.
 *
 * @param modifier El modificador que se aplica al campo de entrada.
 * @param textFieldValue El valor actual del campo de entrada.
 * @param onTextFieldValueChanged La lambda que se llama cuando cambia el valor del campo de entrada.
 */
@Composable
fun UserField(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onTextFieldValueChanged: (String) -> Unit
) {
    // Fila que contiene el icono y el campo de entrada de usuario
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        // Icono de persona
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.White, // Configurar el color del icono
            modifier = Modifier.padding(end = 10.dp, start = 10.dp).size(35.dp)
        )

        // Textfield para el nombre de usuario
        TextField(
            value = textFieldValue,
            label = { Text(text = "Username") }, // Etiqueta del campo de entrada
            onValueChange = onTextFieldValueChanged, // Lambda para manejar cambios en el valor
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // Configuración del teclado para tipo de texto
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(250.dp)
                .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp)), // Estilo del campo de entrada
            singleLine = true // Para evitar que el texto se desborde
        )
    }
}
