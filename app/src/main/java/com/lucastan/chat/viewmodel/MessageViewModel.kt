package com.lucastan.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel(private val messageRepository: MessageRepository) : ViewModel() {
    val messages = messageRepository.messages

    val currentChatId = 1
    val currentUserId = 1

    val messageEditText = MutableLiveData<String>()

    init {
        prepopulateMessages()
    }

    private fun prepopulateMessages() = viewModelScope.launch(Dispatchers.IO) {
        messageRepository.prepopulateMessages()
    }

    fun sendMessage() {
        val typedMessage = messageEditText.value!!

        if (typedMessage.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                messageRepository.insert(Message(currentChatId, currentUserId, typedMessage))

                withContext(Dispatchers.Main) {
                    messageEditText.value = ""
                }
            }
        }
    }
}