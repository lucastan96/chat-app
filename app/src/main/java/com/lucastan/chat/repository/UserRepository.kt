package com.lucastan.chat.repository

import com.lucastan.chat.model.User
import com.lucastan.chat.repository.database.UserDao

class UserRepository(private val dao: UserDao) {
    suspend fun get(userId: Int): User {
        return dao.getUserById(userId)
    }

    suspend fun insert(user: User) {
        dao.insertUser(user)
    }

    suspend fun prepopulateUsers() {
        if (dao.getUsersCount() == 0) {
            val prepopulatedUsers = ArrayList<User>()
            prepopulatedUsers.add(User("Lucas Tan"))
            prepopulatedUsers.add(User("Joe Bloggs"))

            dao.insertUsers(prepopulatedUsers)
        }
    }
}