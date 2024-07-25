package com.lucastan.chat.repository

import android.content.Context
import android.content.SharedPreferences
import com.lucastan.chat.model.Chat
import com.lucastan.chat.repository.database.ChatDao
import com.lucastan.chat.util.Constants.Companion.DEMO_CURRENT_CHAT_ID
import com.lucastan.chat.util.Constants.Companion.DEMO_CURRENT_FRIEND_ID
import com.lucastan.chat.util.Constants.Companion.DEMO_CURRENT_USER_ID
import com.lucastan.chat.util.Constants.Companion.PREF_CURRENT_CHAT_ID
import com.lucastan.chat.util.Constants.Companion.PREF_NAME

class ChatRepository(context: Context, private val dao: ChatDao) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var currentChatId = 0

    init {
        getCurrentChatId()
    }

    suspend fun get(chatId: Int): Chat {
        return dao.getChatById(chatId)
    }

    suspend fun insert(chat: Chat) {
        dao.insertChat(chat)
    }

    suspend fun prepopulateChats() {
        if (dao.getChatsCount() == 0) {
            val prepopulatedChats = ArrayList<Chat>()
            prepopulatedChats.add(Chat(DEMO_CURRENT_USER_ID))
            prepopulatedChats.add(Chat(DEMO_CURRENT_FRIEND_ID))

            dao.insertChats(prepopulatedChats)

            switchCurrentChatId(DEMO_CURRENT_CHAT_ID)
        }
    }

    private fun getCurrentChatId() {
        currentChatId = prefs.getInt(PREF_CURRENT_CHAT_ID, DEMO_CURRENT_CHAT_ID)
    }

    suspend fun switchCurrentChatId(chatId: Int) {
        prefs.edit().putInt(PREF_CURRENT_CHAT_ID, chatId).apply()
    }
}