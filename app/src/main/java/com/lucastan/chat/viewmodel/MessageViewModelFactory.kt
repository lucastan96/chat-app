package com.lucastan.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucastan.chat.repository.MessageRepository

class MessageViewModelFactory(private val messageRepository: MessageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            return MessageViewModel(messageRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}