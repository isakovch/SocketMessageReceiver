package com.example.surrogatemessagereceiver.chat

import com.example.surrogatemessagereceiver.chat.interactor.ChatInteractor
import com.example.surrogatemessagereceiver.chat.repository.ChatRepository
import com.example.surrogatemessagereceiver.chat.repository.ChatRepositoryImpl
import com.example.surrogatemessagereceiver.chat.ui.ChatViewModel
import com.example.surrogatemessagereceiver.socket.SocketUpdatesManager
import com.example.surrogatemessagereceiver.socket.UpdatesManager
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object ChatModule {

    fun create() = module {
        single { Gson() }
        single { SocketUpdatesManager(get()) } bind UpdatesManager::class
        single { ChatRepositoryImpl(get()) } bind ChatRepository::class
        single { ChatInteractor(get()) }

        viewModel { ChatViewModel(get(), get()) }
    }
}