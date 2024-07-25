package com.lucastan.chat.repository

import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.database.MessageDao
import com.lucastan.chat.util.Constants.Companion.DEMO_CURRENT_CHAT_ID
import com.lucastan.chat.util.Constants.Companion.DEMO_CURRENT_FRIEND_ID

class MessageRepository(private val dao: MessageDao) {
    val messages = dao.getAllMessagesByChatId(DEMO_CURRENT_CHAT_ID)

    suspend fun insert(message: Message) {
        dao.insertMessage(message)
    }

    suspend fun delete(message: Message) {
        dao.deleteMessage(message)
    }

    suspend fun prepopulateMessages() {
        if (dao.getMessagesCount() == 0) {
            val prepopulatedMessages = ArrayList<Message>()
            prepopulatedMessages.add(
                Message(
                    DEMO_CURRENT_CHAT_ID,
                    DEMO_CURRENT_FRIEND_ID,
                    "Hey Lucas!"
                )
            )
            prepopulatedMessages.add(
                Message(
                    DEMO_CURRENT_CHAT_ID,
                    DEMO_CURRENT_FRIEND_ID,
                    "What should we do this weekend?"
                )
            )

            dao.insertMessages(prepopulatedMessages)
        }
    }
}