package com.smartalarm.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.smartalarm.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmScreen(navController: NavController) {
    var hours by remember { mutableStateOf("07") }
    var minutes by remember { mutableStateOf("00") }
    var repetition by remember { mutableStateOf("Una vez") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val repetitionOptions = listOf("Una vez", "Diariamente", "Lunes a Viernes", "Fines de semana")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Status bar + header (blue)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("9:41", color = Color.White, fontSize = 14.sp)
                Text("●●●●", color = Color.White, fontSize = 10.sp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Crear Alarma",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Time picker section
            Text(
                text = "HORA",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceVariant, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TimeInput(
                        value = hours,
                        onValueChange = { v ->
                            val clean = v.filter { it.isDigit() }.take(2)
                            if (clean.isEmpty() || clean.toInt() in 0..23) hours = clean
                        }
                    )
                    Text(
                        ":",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Light,
                        color = TextPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    TimeInput(
                        value = minutes,
                        onValueChange = { v ->
                            val clean = v.filter { it.isDigit() }.take(2)
                            if (clean.isEmpty() || clean.toInt() in 0..59) minutes = clean
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Repetition dropdown
            Text(
                text = "REPETICIÓN",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = repetition,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Border,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    repetitionOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                repetition = option
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextPrimary,
                        containerColor = Background
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = false)
                ) {
                    Text("Cancelar", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Guardar", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun TimeInput(value: String, onValueChange: (String) -> Unit) {
    androidx.compose.foundation.text.BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.width(72.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            color = TextPrimary,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {
                if (value.isEmpty()) {
                    Text(
                        "00",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Light,
                        color = TextHint,
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }
        }
    )
}
