package com.example.surrogatemessagereceiver.socket

import com.example.surrogatemessagereceiver.chat.model.MessageResponse

interface UpdatesManager {
    fun start()
    fun stop()
    fun onMessageReceived(onReceived: (MessageResponse) -> Unit)
}