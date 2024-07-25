package com.lucastan.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lucastan.chat.R
import com.lucastan.chat.model.Message
import com.lucastan.chat.util.Measurement
import com.lucastan.chat.util.Time


class ChatViewAdapter(
    private val messages: List<Message>,
    private val currentUserId: Int,
    private val clickListener: (Message) -> Unit
) : RecyclerView.Adapter<ChatViewHolder>() {
    companion object {
        const val INCOMING_CHAT = 1
        const val OUTGOING_CHAT = 2
        const val TIME_HEADING_PERIOD = 60 * 60 * 1000 // 1 hour
        const val SMALLER_SPACING_PERIOD = 20 * 1000 // 20 seconds
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        // Incoming chat will display a left-sided bubble & outgoing will be right-sided
        val item = if (viewType == OUTGOING_CHAT) {
            layoutInflater.inflate(R.layout.item_chat_outgoing, parent, false)
        } else {
            layoutInflater.inflate(R.layout.item_chat_incoming, parent, false)
        }

        return ChatViewHolder(item)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(
            messages[position],
            isTimeHeadingNeeded(position),
            isSmallerSpacingNeeded(position),
            clickListener
        )
    }

    /** Check if time heading above a message is needed **/
    private fun isTimeHeadingNeeded(position: Int): Boolean {
        return position == 0 || messages[position].timestamp - messages[position - 1].timestamp > TIME_HEADING_PERIOD
    }

    /** Check if smaller spacing between message is needed **/
    private fun isSmallerSpacingNeeded(position: Int): Boolean {
        return if (position > 0 && messages[position].userId == messages[position - 1].userId) {
            messages[position].timestamp - messages[position - 1].timestamp < SMALLER_SPACING_PERIOD
        } else {
            false
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userId == currentUserId) OUTGOING_CHAT else INCOMING_CHAT
    }
}

class ChatViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        message: Message,
        isTimeHeadingNeeded: Boolean,
        isSmallerSpacingNeeded: Boolean,
        clickListener: (Message) -> Unit
    ) {
        val bubble: ConstraintLayout = view.findViewById(R.id.bubble)
        val time: TextView = view.findViewById(R.id.time)
        val text: TextView = view.findViewById(R.id.text)

        if (isTimeHeadingNeeded) {
            // Format time before assigning it to text view
            time.text = Time().formatTime(message.timestamp)
            time.visibility = View.VISIBLE
        }

        if (isTimeHeadingNeeded || isSmallerSpacingNeeded) {
            // Adjust bubble top margin
            val params: ConstraintLayout.LayoutParams = bubble.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = Measurement().convertDpToPx(view.context, 4f)
            bubble.setLayoutParams(params)
        }

        text.text = message.text

        view.setOnClickListener {
            clickListener(message)
        }
    }
}