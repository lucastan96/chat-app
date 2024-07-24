package com.example.chatsample.repository

import com.example.chatsample.model.Message
import com.example.chatsample.repository.database.MessageDao

class MessageRepository(private val dao: MessageDao) {
    companion object {
        const val DEFAULT_CHAT_ID = 1
    }

    val messages = dao.getAllMessagesByChatId(DEFAULT_CHAT_ID)

    suspend fun insert(message: Message) {
        dao.insertMessage(message)
    }

    suspend fun delete(message: Message) {
        dao.deleteMessage(message)
    }
}