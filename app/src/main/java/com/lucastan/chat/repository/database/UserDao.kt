package com.lucastan.chat.repository.database

import androidx.room.Dao
import androidx.room.Insert
import com.lucastan.chat.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)
}