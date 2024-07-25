package com.lucastan.chat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val chatId: Int,
    val userId: Int,
    val text: String,
    val timestamp: Long,
    val isRead: Boolean
) {
    constructor(chatId: Int, userId: Int, text: String) : this(0, chatId, userId, text, System.currentTimeMillis(), false)
}