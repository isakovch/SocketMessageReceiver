package com.example.surrogatemessagereceiver.chat.repository

import com.example.surrogatemessagereceiver.chat.model.MessageConverter
import com.example.surrogatemessagereceiver.chat.model.ChatMessage
import com.example.surrogatemessagereceiver.socket.UpdatesManager
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import timber.log.Timber

interface ChatRepository {
    fun getMessagesFlowable(): Flowable<List<ChatMessage>>
}

class ChatRepositoryImpl(
    updatesManager: UpdatesManager
) : ChatRepository {

    private val messagesProcessor = BehaviorProcessor.create<MutableList<ChatMessage>>()

    init {
        updatesManager.onMessageReceived { response ->
            if (!response.isMessage()) {
                Timber.w("Response is incorrect, skipping")
                return@onMessageReceived
            }

            val messages = messagesProcessor.value ?: mutableListOf()
            val newMessage = MessageConverter.fromNetwork(response)
            messages.add(newMessage)
            messagesProcessor.offer(messages)
        }
    }

    override fun getMessagesFlowable(): Flowable<List<ChatMessage>> = messagesProcessor.map { it }
}