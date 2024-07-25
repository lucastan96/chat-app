package com.lucastan.chat.util

class Constants {
    companion object {
        // Constant values for ChatViewAdapter
        const val INCOMING_CHAT = 1
        const val OUTGOING_CHAT = 2
        const val TIME_HEADING_PERIOD = 60 * 60 * 1000 // 1 hour
        const val SMALLER_SPACING_PERIOD = 20 * 1000 // 20 seconds

        // SharedPreferences
        const val PREF_NAME = "COM_LUCASTAN_CHAT"
        const val PREF_CURRENT_USER_ID = "PREF_CURRENT_USER_ID"

        // Demo IDs
        const val DEMO_CURRENT_USER_ID = 1
        const val DEMO_CURRENT_CHAT_ID = 1
    }
}