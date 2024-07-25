package com.lucastan.chat.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.lucastan.chat.R
import com.lucastan.chat.adapter.ChatViewAdapter
import com.lucastan.chat.databinding.ActivityMainBinding
import com.lucastan.chat.model.Message
import com.lucastan.chat.repository.ChatRepository
import com.lucastan.chat.repository.MessageRepository
import com.lucastan.chat.repository.UserRepository
import com.lucastan.chat.repository.database.ChatDatabase
import com.lucastan.chat.viewmodel.ChatViewModel
import com.lucastan.chat.viewmodel.MessageViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: ChatViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set up database and view model
        setupDatabase()

        // Set up messages list
        initRecyclerView()

        // Set up input field & send button
        setupInput()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toggleRestart.collect {
                    if (it) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun setupDatabase() {
        // Build database
        val database = Room.databaseBuilder(
            applicationContext,
            ChatDatabase::class.java, "chat-database"
        ).build()

        // Initialize DAO, repository & view model
        val userDao = database.userDao()
        val userRepository = UserRepository(applicationContext, userDao)

        val chatDao = database.chatDao()
        val chatRepository = ChatRepository(applicationContext, chatDao)

        val messageDao = database.messageDao()
        val messageRepository = MessageRepository(messageDao)

        val factory = MessageViewModelFactory(userRepository, chatRepository, messageRepository)
        viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        binding.messageViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ChatViewAdapter { selectedItem: Message ->
            listItemClicked(selectedItem)
        }
        binding.recyclerView.adapter = adapter

        displayMessagesList()
    }

    private fun displayMessagesList() {
        // Listen for LiveData changes
        viewModel.messages.observe(this) {
            adapter.setData(it, viewModel.currentUserId)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupInput() {
        viewModel.inputMessage.observe(this) {
            var backgroundDrawable = if (it.isNotEmpty()) {
                R.drawable.bg_rectangle_pink
            } else {
                R.drawable.bg_rectangle_gray
            }

            binding.messageEditText.background = ContextCompat.getDrawable(this, backgroundDrawable)
            binding.sendBtn.isEnabled = it.isNotEmpty()
        }

        binding.messageEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    viewModel.sendMessage()
                    true
                }
                else -> false
            }
        }
    }

    private fun listItemClicked(message: Message) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate swap user button in toolbar
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_swap -> {
                viewModel.swapUser()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}