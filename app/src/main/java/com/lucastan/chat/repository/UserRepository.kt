package com.lucastan.chat.repository

import com.lucastan.chat.model.User
import com.lucastan.chat.repository.database.UserDao

class UserRepository(private val dao: UserDao) {
    suspend fun insert(user: User) {
        dao.insertUser(user)
    }
}