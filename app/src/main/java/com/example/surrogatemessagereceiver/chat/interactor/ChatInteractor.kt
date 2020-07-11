package com.example.surrogatemessagereceiver.chat.interactor

import com.example.surrogatemessagereceiver.chat.model.ChatMessage
import com.example.surrogatemessagereceiver.chat.repository.ChatRepository
import io.reactivex.Flowable

class ChatInteractor(
    private val chatRepository: ChatRepository
) {

    fun getMessagesFlowable(): Flowable<List<ChatMessage>> =
        chatRepository.getMessagesFlowable()
}