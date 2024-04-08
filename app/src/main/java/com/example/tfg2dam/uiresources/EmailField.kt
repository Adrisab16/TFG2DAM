package com.example.tfg2dam.uiresources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.relay.compose.ColumnScopeInstanceImpl.weight


@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onTextFieldValueChanged: (String) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null,
            tint = Color.White, // Configurar el color del icono
            modifier = Modifier.padding(end = 10.dp, start = 10.dp).size(35.dp)
        )
        // Zona para introducir datos
        TextField(
            value = textFieldValue,
            label = { Text(text = "Email")},
            onValueChange = onTextFieldValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(200.dp)
                .padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp)),
            singleLine = true // Para evitar que el texto se desborde
        )
    }
}



