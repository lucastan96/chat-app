package com.lucastan.chat.repository

import android.content.Context
import android.content.SharedPreferences
import com.lucastan.chat.model.User
import com.lucastan.chat.repository.database.UserDao
import com.lucastan.chat.util.Constants.Companion.DEMO_CURRENT_USER_ID
import com.lucastan.chat.util.Constants.Companion.PREF_CURRENT_USER_ID
import com.lucastan.chat.util.Constants.Companion.PREF_NAME

class UserRepository(context: Context, private val dao: UserDao) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var currentUserId = 0

    init {
        getCurrentUserId()
    }

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

            switchCurrentUserId(DEMO_CURRENT_USER_ID)
        }
    }

    private fun getCurrentUserId() {
        currentUserId = prefs.getInt(PREF_CURRENT_USER_ID, DEMO_CURRENT_USER_ID)
    }

    private fun switchCurrentUserId(userId: Int) {
        prefs.edit().putInt(PREF_CURRENT_USER_ID, userId).apply()
    }
}