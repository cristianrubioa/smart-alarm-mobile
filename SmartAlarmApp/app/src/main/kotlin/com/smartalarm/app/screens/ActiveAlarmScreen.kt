package com.smartalarm.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.smartalarm.app.ui.theme.*
import java.util.Calendar

@Composable
fun ActiveAlarmScreen(navController: NavController, alarmId: String) {
    var showSnoozeModal by remember { mutableStateOf(false) }
    var selectedSnoozeMinutes by remember { mutableStateOf(5) }

    fun calculateNewTime(addMinutes: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 7)
        cal.set(Calendar.MINUTE, 0)
        cal.add(Calendar.MINUTE, addMinutes)
        val h = cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
        val m = cal.get(Calendar.MINUTE).toString().padStart(2, '0')
        return "$h:$m"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Status bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary)
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("9:41", color = Color.White, fontSize = 14.sp)
                Text("●●●●", color = Color.White, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Alarm icon
            Text(text = "⏰", fontSize = 120.sp, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(24.dp))

            // Time
            Text(
                text = "07:00",
                fontSize = 96.sp,
                fontWeight = FontWeight.ExtraLight,
                color = TextPrimary,
                letterSpacing = (-2).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Alarma",
                fontSize = 18.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Snooze button
                Button(
                    onClick = { showSnoozeModal = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("🔔", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Snooze",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }

                Text(
                    "Posponer 5 minutos",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )

                // Stop button
                Button(
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("🔕", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Apagar",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Snooze modal dialog
        if (showSnoozeModal) {
            Dialog(onDismissRequest = { showSnoozeModal = false }) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Icon circle
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔔", fontSize = 36.sp)
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            "¿Posponer alarma?",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Selecciona cuánto tiempo quieres posponer",
                            fontSize = 14.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Snooze time options
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(5, 10, 15).forEach { mins ->
                                val isSelected = selectedSnoozeMinutes == mins
                                Button(
                                    onClick = { selectedSnoozeMinutes = mins },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) Primary else SurfaceVariant,
                                        contentColor = if (isSelected) Color.White else TextPrimary
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                                ) {
                                    Text("$mins min", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // New time preview
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
                                    calculateNewTime(selectedSnoozeMinutes),
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Light,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "SONARÁ NUEVAMENTE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextSecondary,
                                    letterSpacing = 1.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Confirm snooze
                        Button(
                            onClick = {
                                showSnoozeModal = false
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                        ) {
                            Text("Posponer", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = {
                                showSnoozeModal = false
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TextPrimary,
                                containerColor = Background
                            ),
                            border = ButtonDefaults.outlinedButtonBorder(enabled = false)
                        ) {
                            Text("Apagar", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}
