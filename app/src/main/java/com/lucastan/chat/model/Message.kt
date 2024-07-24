package com.example.chatsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val chatId: Int,
    val userId: Int,
    val text: String,
    val isRead: Boolean
) {
    constructor(chatId: Int, userId: Int, text: String) : this(0, chatId, userId, text, false)
}