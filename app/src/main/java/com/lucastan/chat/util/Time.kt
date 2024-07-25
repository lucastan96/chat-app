package com.lucastan.chat.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Time {
    /** Formats time in a Day/Month 24-Hour:Minute format **/
    fun formatTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
        return format.format(date)
    }
}