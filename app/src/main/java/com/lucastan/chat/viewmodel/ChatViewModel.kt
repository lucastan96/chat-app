package com.lucastan.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.MessageRepository
import com.lucastan.chat.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(private val userRepository: UserRepository, private val messageRepository: MessageRepository) : ViewModel() {
    val currentUserId = userRepository.currentUserId
    val currentChatId = 1
    val messages = messageRepository.messages

    val friendName = "Lucas"
    val messageEditText = MutableLiveData<String>()

    init {
        // This should be removed in a real-world scenario as data is prepopulated for demonstration purposes
        prepopulateData()
    }

    private fun prepopulateData() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.prepopulateUsers()
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

    fun swapUser() {

    }
}