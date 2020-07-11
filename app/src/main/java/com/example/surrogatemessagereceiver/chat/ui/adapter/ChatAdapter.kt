package com.example.surrogatemessagereceiver.chat.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surrogatemessagereceiver.R
import com.example.surrogatemessagereceiver.chat.model.ChatMessage

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = listOf<ChatMessage>()

    fun setItems(new: List<ChatMessage>) {
        data = new
        notifyItemInserted(data.size)
    }

    override fun getItemViewType(position: Int): Int {
        val previous = if (position > 0) data[position - 1] else null
        val current = data[position]

        return if (current.isSameAuthor(previous?.userId)) {
            R.layout.item_message_rounded
        } else {
            R.layout.item_message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        R.layout.item_message_rounded -> ChatViewHolder(parent)
        R.layout.item_message -> ChatAuthorViewHolder(parent)
        else -> throw IllegalStateException("Unknown type $viewType")
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatViewHolder -> holder.onBind(data[position])
            is ChatAuthorViewHolder -> holder.onBind(data[position])
        }
    }
}