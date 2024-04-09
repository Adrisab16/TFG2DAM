package com.example.tfg2dam.uiresources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
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

@Composable
fun UserField(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onTextFieldValueChanged: (String) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.White, // Configurar el color del icono
            modifier = Modifier.padding(end = 10.dp, start = 10.dp).size(35.dp)
        )
        // Zona para introducir datos
        TextField(
            value = textFieldValue,
            label = { Text(text = "Username") },
            onValueChange = onTextFieldValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(250.dp)
                .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp)),
            singleLine = true // Para evitar que el texto se desborde
        )
    }
}
