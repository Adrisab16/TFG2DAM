package com.example.tfg2dam.uiresources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Función componible que muestra un campo de entrada de contraseña con un icono para mostrar u ocultar la contraseña.
 *
 * @param modifier El modificador que se aplica al campo de entrada.
 * @param textFieldValue El valor actual del campo de entrada.
 * @param onTextFieldValueChanged La lambda que se llama cuando cambia el valor del campo de entrada.
 */
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onTextFieldValueChanged: (String) -> Unit
) {

    // Estado para controlar la visibilidad de la contraseña
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Fila que contiene el icono y el campo de entrada de contraseña
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        // Icono de candado
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            tint = Color.White, // Color del icono
            modifier = Modifier.padding(end = 10.dp, start = 10.dp).size(35.dp)
        )

        // Textfield para la contraseña
        TextField(
            value = textFieldValue,
            label = { Text(text = "Password")}, // Etiqueta del campo de entrada
            onValueChange = onTextFieldValueChanged, // Lambda para manejar cambios en el valor
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Transformación visual para ocultar la contraseña
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Configuración del teclado para tipo de contraseña
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(250.dp)
                .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp)), // Estilo del campo de entrada
            singleLine = true, // Para evitar que el texto se desborde
            trailingIcon = {
                // Icono para mostrar u ocultar la contraseña
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = Color.White
                    )
                }
            }
        )
    }
}