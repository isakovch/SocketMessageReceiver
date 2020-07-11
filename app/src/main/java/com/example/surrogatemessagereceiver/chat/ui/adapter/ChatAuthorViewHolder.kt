package com.example.surrogatemessagereceiver.chat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surrogatemessagereceiver.R
import com.example.surrogatemessagereceiver.chat.model.ChatMessage
import kotlinx.android.synthetic.main.item_message.view.messageTextView
import kotlinx.android.synthetic.main.item_message.view.nameTextView

class ChatAuthorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
) {

    private val nameTextView = itemView.nameTextView
    private val messageTextView = itemView.messageTextView

    fun onBind(item: ChatMessage) {
        nameTextView.text = item.name
        messageTextView.text = item.message
    }
}