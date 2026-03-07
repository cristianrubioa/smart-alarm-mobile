package com.smartalarm.app.data

data class Alarm(
    val id: String,
    val time: String,
    val enabled: Boolean,
    val repeat: String = "Una vez"
)

object AlarmRepository {
    val alarms = mutableListOf(
        Alarm("1", "07:00", true, "Diariamente"),
        Alarm("2", "12:30", true, "Lunes a Viernes"),
        Alarm("3", "18:00", false, "Una vez"),
        Alarm("4", "22:00", true, "Diariamente"),
    )

    fun getById(id: String): Alarm? = alarms.find { it.id == id }
}
