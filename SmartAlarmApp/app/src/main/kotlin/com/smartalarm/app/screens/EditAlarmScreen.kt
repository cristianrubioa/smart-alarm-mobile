package com.smartalarm.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.smartalarm.app.data.AlarmRepository
import com.smartalarm.app.ui.theme.*

@Composable
fun EditAlarmScreen(navController: NavController, alarmId: String) {
    val alarm = AlarmRepository.getById(alarmId)
    var hours by remember { mutableStateOf(alarm?.time?.substringBefore(":") ?: "12") }
    var minutes by remember { mutableStateOf(alarm?.time?.substringAfter(":") ?: "30") }
    var isActive by remember { mutableStateOf(alarm?.enabled ?: true) }
    var showDeleteModal by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
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
                        text = "Editar Alarma",
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
                // Hora
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
                        EditTimeInput(
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
                        EditTimeInput(
                            value = minutes,
                            onValueChange = { v ->
                                val clean = v.filter { it.isDigit() }.take(2)
                                if (clean.isEmpty() || clean.toInt() in 0..59) minutes = clean
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Estado de la alarma (switch)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceVariant, RoundedCornerShape(16.dp))
                        .border(1.dp, Border, RoundedCornerShape(16.dp))
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "ESTADO DE LA ALARMA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextSecondary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isActive) "Activa" else "Inactiva",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isActive) TextPrimary else TextHint
                            )
                        }
                        Switch(
                            checked = isActive,
                            onCheckedChange = { isActive = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Primary,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = SwitchTrackOff,
                                uncheckedBorderColor = Border
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Info text
                Text(
                    text = "Solo puedes modificar la hora y activar/desactivar la alarma",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Cancelar / Guardar row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.weight(1f).height(56.dp),
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
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Guardar", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Delete button
                Button(
                    onClick = { showDeleteModal = true },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                ) {
                    Text("Eliminar Alarma", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Delete confirmation modal
        if (showDeleteModal) {
            Dialog(onDismissRequest = { showDeleteModal = false }) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Warning icon circle
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(WarningOrange, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⚠️", fontSize = 36.sp)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            "¿Eliminar alarma?",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Esta acción no se puede deshacer",
                            fontSize = 15.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Alarm info box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceVariant, RoundedCornerShape(16.dp))
                                .border(1.dp, Border, RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "$hours:$minutes",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Light,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Primary
                                ) {
                                    Text(
                                        alarm?.repeat ?: "Diario",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // No / Sí row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showDeleteModal = false },
                                modifier = Modifier.weight(1f).height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = TextPrimary,
                                    containerColor = Background
                                ),
                                border = ButtonDefaults.outlinedButtonBorder(enabled = false)
                            ) {
                                Text("No", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }
                            Button(
                                onClick = {
                                    showDeleteModal = false
                                    navController.navigate("home")
                                },
                                modifier = Modifier.weight(1f).height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                            ) {
                                Text("Sí, eliminar", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Se eliminará permanentemente",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EditTimeInput(value: String, onValueChange: (String) -> Unit) {
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
