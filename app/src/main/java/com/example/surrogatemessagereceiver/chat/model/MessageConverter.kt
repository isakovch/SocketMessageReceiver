package com.example.surrogatemessagereceiver.chat.model

object MessageConverter {

    fun fromNetwork(response: MessageResponse): ChatMessage =
        ChatMessage(
            userId = response.userId,
            name = response.username,
            message = response.message
        )
}