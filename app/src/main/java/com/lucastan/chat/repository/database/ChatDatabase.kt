package com.example.chatsample.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatsample.model.Message
import com.example.chatsample.model.User

@Database(entities = [User::class, Message::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
}