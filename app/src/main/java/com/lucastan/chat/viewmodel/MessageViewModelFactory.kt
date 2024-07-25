package com.lucastan.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucastan.chat.repository.MessageRepository
import com.lucastan.chat.repository.UserRepository

class MessageViewModelFactory(private val userRepository: UserRepository, private val messageRepository: MessageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(userRepository, messageRepository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}