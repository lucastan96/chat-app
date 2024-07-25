package com.lucastan.chat.repository

import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.database.MessageDao

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

    suspend fun prepopulateMessages() {
        if (dao.getMessagesCount() == 0) {
            val prepopulatedMessages = ArrayList<Message>()
            prepopulatedMessages.add(Message(1, 2, "Hello there!"))
            prepopulatedMessages.add(Message(1, 1, "Hey, it's been a while! How are you?"))

            dao.insertMessages(prepopulatedMessages)
        }
    }
}