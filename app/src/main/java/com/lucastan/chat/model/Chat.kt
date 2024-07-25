package com.lucastan.chat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val friendId: Int
) {
    constructor(friendId: Int) : this(0, friendId)
}