package com.example.surrogatemessagereceiver.chat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surrogatemessagereceiver.R
import com.example.surrogatemessagereceiver.chat.model.ChatMessage

class ChatViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_message_rounded, parent, false)
) {

    private val messageTextView = itemView as TextView

    fun onBind(item: ChatMessage) {
        messageTextView.text = item.message
    }
}