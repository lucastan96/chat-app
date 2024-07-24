package com.lucastan.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel(private val messageRepository: MessageRepository) : ViewModel() {
    val messages = messageRepository.messages
    val currentUserId = 1

    private fun sendMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.insert(message)

        withContext(Dispatchers.Main) {

        }
    }
}