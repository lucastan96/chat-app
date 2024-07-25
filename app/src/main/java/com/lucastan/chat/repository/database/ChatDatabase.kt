package com.lucastan.chat.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucastan.chat.model.Chat
import com.lucastan.chat.model.Message
import com.lucastan.chat.model.User

@Database(entities = [User::class, Chat::class, Message::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
}