package com.lucastan.chat.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lucastan.chat.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertUsers(users: List<User>)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): User

    @Query("SELECT COUNT(id) FROM user")
    suspend fun getUsersCount(): Int
}