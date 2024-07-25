package com.lucastan.chat.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lucastan.chat.model.Chat

@Dao
interface ChatDao {
    @Insert
    suspend fun insertChat(chat: Chat)

    @Insert
    suspend fun insertChats(chat: List<Chat>)

    @Query("SELECT * FROM chat WHERE id = :id")
    fun getChatById(id: Int): Chat

    @Query("SELECT COUNT(id) FROM chat")
    suspend fun getChatsCount(): Int
}