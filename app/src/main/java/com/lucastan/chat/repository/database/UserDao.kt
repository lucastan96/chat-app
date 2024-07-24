package com.example.chatsample.repository.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.chatsample.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)
}