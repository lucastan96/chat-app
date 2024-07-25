package com.lucastan.chat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) {
    constructor(name: String) : this(0, name)
}