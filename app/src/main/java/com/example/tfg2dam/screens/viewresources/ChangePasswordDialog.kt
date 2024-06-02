package com.example.tfg2dam.screens.viewresources

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Diálogo para cambiar la contraseña del usuario.
 * @param onDismiss Acción a realizar al descartar el diálogo.
 * @param onConfirm Acción a realizar al confirmar el cambio de contraseña. Recibe la contraseña anterior y la nueva contraseña como parámetros.
 */
@Composable
fun ChangePasswordDialog(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    // Estado para almacenar la contraseña anterior
    var oldPassword by remember { mutableStateOf("") }
    // Estado para almacenar la nueva contraseña
    var newPassword by remember { mutableStateOf("") }
    // Contexto actual
    val context = LocalContext.current

    // Diálogo de alerta para cambiar la contraseña
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cambiar contraseña") },
        text = {
            Column {
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = { Text("Contraseña anterior") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("Nueva contraseña") },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (oldPassword.isNotEmpty() && newPassword.isNotEmpty()) {
                        onConfirm(oldPassword, newPassword)
                    } else {
                        Toast.makeText(context, "Por favor, rellene ambos campos", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}