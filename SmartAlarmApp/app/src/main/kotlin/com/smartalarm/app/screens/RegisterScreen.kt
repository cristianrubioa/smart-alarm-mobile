package com.smartalarm.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.smartalarm.app.ui.theme.*

@Composable
fun RegisterScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Logo
            Text(text = "⏰", fontSize = 64.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Alarmas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                color = TextPrimary
            )
            Text(text = "Gestiona tu tiempo", fontSize = 13.sp, color = TextSecondary)

            Spacer(modifier = Modifier.height(24.dp))

            // Form header
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Crear cuenta",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Es rápido y fácil", fontSize = 14.sp, color = TextSecondary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre
            LabeledTextField(
                label = "NOMBRE",
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = "Juan"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Apellido
            LabeledTextField(
                label = "APELLIDO",
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = "Pérez"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            LabeledTextField(
                label = "EMAIL",
                value = email,
                onValueChange = { email = it },
                placeholder = "tu@email.com",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            LabeledTextField(
                label = "CONTRASEÑA",
                value = password,
                onValueChange = { password = it },
                placeholder = "••••••••",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            LabeledTextField(
                label = "CONFIRMAR CONTRASEÑA",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "••••••••",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Terms checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Primary,
                        uncheckedColor = Border,
                        checkmarkColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Acepto los términos y condiciones",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Register button
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text(
                    "Crear cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login link
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("¿Ya tienes cuenta? ", color = TextSecondary, fontSize = 14.sp)
                TextButton(
                    onClick = { navController.navigate("login") },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Inicia sesión",
                        color = Primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = TextHint) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Border,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = Primary
            )
        )
    }
}
