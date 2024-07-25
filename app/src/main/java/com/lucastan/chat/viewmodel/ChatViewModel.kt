package com.lucastan.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.ChatRepository
import com.lucastan.chat.repository.MessageRepository
import com.lucastan.chat.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {
    val currentUserId = userRepository.currentUserId
    private val currentChatId = chatRepository.currentChatId

    val messages = messageRepository.messages

    val friendName = MutableLiveData<String>()

    val inputMessage = MutableLiveData<String>()

    private val _toggleRestart = MutableSharedFlow<Boolean>()
    val toggleRestart: SharedFlow<Boolean> = _toggleRestart

    init {
        initData()
    }

    private fun initData() = viewModelScope.launch {
        // This should be removed in a real-world scenario as data is prepopulated for demonstration purposes
        val deferredResult = async(Dispatchers.IO) {
            userRepository.prepopulateUsers()
            chatRepository.prepopulateChats()
            messageRepository.prepopulateMessages()
        }
        deferredResult.await()

        viewModelScope.launch(Dispatchers.IO) {
            val friendId = chatRepository.get(currentChatId).friendId
            val user = userRepository.get(friendId)

            withContext(Dispatchers.Main) {
                friendName.value = user.name
            }
        }
    }

    fun sendMessage() {
        val typedMessage = inputMessage.value!!

        if (typedMessage.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                messageRepository.insert(Message(currentChatId, currentUserId, typedMessage))

                withContext(Dispatchers.Main) {
                    inputMessage.value = ""
                }
            }
        }
    }

    fun swapUser() = viewModelScope.launch(Dispatchers.IO) {
        if (currentUserId == 1) {
            userRepository.switchCurrentUserId(2)
            chatRepository.switchCurrentChatId(2)
        } else {
            userRepository.switchCurrentUserId(1)
            chatRepository.switchCurrentChatId(1)
        }

        withContext(Dispatchers.Main) {
            _toggleRestart.emit(true)
        }
    }
}