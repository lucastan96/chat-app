package com.lucastan.chat.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lucastan.chat.model.Message

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message WHERE chatId = :chatId")
    fun getAllMessagesByChatId(chatId: Int): LiveData<List<Message>>
}