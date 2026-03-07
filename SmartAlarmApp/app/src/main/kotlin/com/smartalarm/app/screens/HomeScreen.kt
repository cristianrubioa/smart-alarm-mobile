package com.smartalarm.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.smartalarm.app.data.Alarm
import com.smartalarm.app.data.AlarmRepository
import com.smartalarm.app.ui.theme.*

@Composable
fun HomeScreen(navController: NavController) {
    val alarms = remember { mutableStateListOf<Alarm>().also { it.addAll(AlarmRepository.alarms) } }

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Status bar + header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary)
            ) {
                // Status bar
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

                // Header row
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Mis alarmas",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    // Logout button
                    IconButton(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                    }
                }

                // Simulate active alarm button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(
                        onClick = { navController.navigate("active/1") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                            brush = androidx.compose.ui.graphics.SolidColor(Color.White)
                        ),
                        modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            "Simular Alarma",
                            color = Primary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Alarm list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 0.dp)
            ) {
                items(alarms, key = { it.id }) { alarm ->
                    AlarmCard(
                        alarm = alarm,
                        onToggle = {
                            val index = alarms.indexOfFirst { it.id == alarm.id }
                            if (index >= 0) {
                                alarms[index] = alarm.copy(enabled = !alarm.enabled)
                            }
                        },
                        onClick = { navController.navigate("edit/${alarm.id}") }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }

        // FAB
        FloatingActionButton(
            onClick = { navController.navigate("create") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 32.dp),
            containerColor = Primary,
            contentColor = Color.White,
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Nueva alarma", modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
private fun AlarmCard(alarm: Alarm, onToggle: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = alarm.time,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Light,
                    color = if (alarm.enabled) TextPrimary else TextHint
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = alarm.repeat,
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
            AlarmSwitch(checked = alarm.enabled, onCheckedChange = { onToggle() })
        }
    }
}

@Composable
fun AlarmSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Primary,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = SwitchTrackOff,
            uncheckedBorderColor = Border
        )
    )
}
