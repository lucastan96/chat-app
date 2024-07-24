package com.lucastan.chat.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucastan.chat.model.Message
import com.lucastan.chat.model.User

@Database(entities = [User::class, Message::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
}