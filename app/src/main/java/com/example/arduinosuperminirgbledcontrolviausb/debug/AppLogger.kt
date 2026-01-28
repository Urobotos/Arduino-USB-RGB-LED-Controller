package com.example.arduinosuperminirgbledcontrolviausb.debug

import androidx.compose.runtime.mutableStateListOf

object AppLogger {

    val logs = mutableStateListOf<String>()

    fun log(tag: String, message: String) {

        val cleanMessage = message
            .replace("\r\n", " ")
            .replace("\n", " ")
            .trim()

        val timestamp = System.currentTimeMillis() % 100000
        val line = "[$timestamp] $tag: $cleanMessage"

        // === ODDĚLENÍ BLOKŮ SEND ===
        if (tag == "BTN_SEND" && logs.isNotEmpty()) {
            // vložíme prázdný řádek před nový SEND blok
            if (logs.first().isNotBlank()) {
                logs.add(0, "")
            }
        }

        logs.add(0, line)

        if (logs.size > 200) {
            logs.removeAt(logs.lastIndex)
        }
    }

    fun clear() {
        logs.clear()
    }
}




