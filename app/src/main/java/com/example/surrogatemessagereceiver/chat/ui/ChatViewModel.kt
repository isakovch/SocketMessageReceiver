package com.example.surrogatemessagereceiver.chat.ui

import androidx.lifecycle.MutableLiveData
import com.example.surrogatemessagereceiver.chat.interactor.ChatInteractor
import com.example.surrogatemessagereceiver.chat.model.ChatMessage
import com.example.surrogatemessagereceiver.common.BaseViewModel
import com.example.surrogatemessagereceiver.socket.UpdatesManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ChatViewModel(
    private val updatesManager: UpdatesManager,
    chatInteractor: ChatInteractor
) : BaseViewModel() {

    val messagesLiveData = MutableLiveData<List<ChatMessage>>()

    init {
        chatInteractor.getMessagesFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { messagesLiveData.postValue(it) },
                onError = { Timber.e(it, "Error getting messages") }
            )
            .disposeOnCleared()

        updatesManager.start()
    }

    override fun onCleared() {
        updatesManager.stop()
        super.onCleared()
    }
}