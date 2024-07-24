package com.lucastan.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lucastan.chat.R
import com.lucastan.chat.model.Message

class ChatViewAdapter(
    private val messages: List<Message>,
    private val currentUserId: Int,
    private val clickListener: (Message) -> Unit
) : RecyclerView.Adapter<ChatViewHolder>() {
    companion object {
        const val INCOMING_CHAT = 1
        const val OUTGOING_CHAT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val item = if (viewType == OUTGOING_CHAT) {
            layoutInflater.inflate(R.layout.item_chat_outgoing, parent, false)
        } else {
            layoutInflater.inflate(R.layout.item_chat_incoming, parent, false)
        }

        return ChatViewHolder(item)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position], clickListener)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userId == currentUserId) OUTGOING_CHAT else INCOMING_CHAT
    }
}

class ChatViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(message: Message, clickListener: (Message) -> Unit) {
        val text: TextView = view.findViewById(R.id.text)
        text.text = message.text

        view.setOnClickListener {
            clickListener(message)
        }
    }
}