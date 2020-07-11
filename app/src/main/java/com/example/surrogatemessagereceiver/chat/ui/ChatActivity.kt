package com.example.surrogatemessagereceiver.chat.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surrogatemessagereceiver.R
import com.example.surrogatemessagereceiver.chat.ui.adapter.ChatAdapter
import com.example.surrogatemessagereceiver.utils.observeNonNull
import kotlinx.android.synthetic.main.activity_main.emptyTextView
import kotlinx.android.synthetic.main.activity_main.messagesRecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {

    private val chatViewModel: ChatViewModel by viewModel()

    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(messagesRecyclerView) {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }

        chatViewModel.messagesLiveData.observeNonNull(this) { messages ->
            chatAdapter.setItems(messages)

            val isEmpty = messages.isEmpty()
            messagesRecyclerView.isVisible = !isEmpty
            emptyTextView.isVisible = isEmpty
        }
    }
}