package com.lucastan.chat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.lucastan.chat.databinding.ActivityMainBinding
import com.lucastan.chat.repository.MessageRepository
import com.lucastan.chat.repository.database.ChatDatabase
import com.lucastan.chat.viewmodel.MessageViewModel
import com.lucastan.chat.viewmodel.MessageViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up toolbar
        setSupportActionBar(binding.toolbar)

        val database = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java, "chat-database"
        ).build()

        val messageDao = database.messageDao()
        val messageRepository = MessageRepository(messageDao)
        val factory = MessageViewModelFactory(messageRepository)
        viewModel = ViewModelProvider(this, factory)[MessageViewModel::class.java]

        binding.messageViewModel = viewModel
        binding.lifecycleOwner = this
    }
}