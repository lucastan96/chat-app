package com.example.chatsample.repository

import com.example.chatsample.model.User
import com.example.chatsample.repository.database.UserDao

class UserRepository(private val dao: UserDao) {
    suspend fun insert(user: User) {
        dao.insertUser(user)
    }
}