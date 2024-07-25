package com.lucastan.chat.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.lucastan.chat.R
import com.lucastan.chat.adapter.ChatViewAdapter
import com.lucastan.chat.databinding.ActivityMainBinding
import com.lucastan.chat.model.Message
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

        // Set up toolbar
        setSupportActionBar(binding.toolbar)

        // Build database
        val database = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java, "chat-database"
        ).build()

        // Initialize DAO, repository & view model
        val messageDao = database.messageDao()
        val messageRepository = MessageRepository(messageDao)
        val factory = MessageViewModelFactory(messageRepository)
        viewModel = ViewModelProvider(this, factory)[MessageViewModel::class.java]

        binding.messageViewModel = viewModel
        binding.lifecycleOwner = this

        // Set up messages list
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayMessagesList()
    }

    private fun displayMessagesList() {
        // Listen for LiveData changes
        viewModel.messages.observe(this) {
            binding.recyclerView.adapter =
                ChatViewAdapter(it, viewModel.currentUserId) { selectedItem: Message ->
                    listItemClicked(selectedItem)
                }
        }
    }

    private fun listItemClicked(message: Message) {

    }
}