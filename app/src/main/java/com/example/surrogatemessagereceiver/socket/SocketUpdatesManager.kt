package com.example.surrogatemessagereceiver.socket

import com.example.surrogatemessagereceiver.chat.model.MessageResponse
import com.example.surrogatemessagereceiver.config.AppConstants
import com.example.surrogatemessagereceiver.config.AppConstants.ROOM_ID
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.engineio.client.transports.WebSocket
import com.github.nkzawa.socketio.client.Ack
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import org.json.JSONObject
import timber.log.Timber

class SocketUpdatesManager(
    private val gson: Gson
) : UpdatesManager {

    companion object {
        private const val EVENT_TAG = "SOCKET_EVENT"
        private const val EVENT_SUBSCRIBE = "subscribe"
        private const val EVENT_UNSUBSCRIBE = "unsubscribe"
    }

    private var messageCallback: ((MessageResponse) -> Unit)? = null

    private val socket: Socket

    private var isStarted = false

    init {
        val options = IO.Options()
        options.transports = arrayOf(WebSocket.NAME)
        socket = IO.socket(AppConstants.MB_URL, options)
    }

    override fun start() {
        if (isStarted) {
            Timber.tag(EVENT_TAG).d("Trying to start updates manager while it is connecting")
            return
        }

        isStarted = true
        Timber.tag(EVENT_TAG).d("Starting updates manager connection")
        socket.connect()
        socket.on(Socket.EVENT_CONNECT, onConnectListener)
        socket.on(Socket.EVENT_DISCONNECT, onDisconnectListener)
    }

    override fun stop() {
        if (!socket.connected()) {
            Timber.tag(EVENT_TAG).d("Trying to stop updates manager while it is already stopped")
            return
        }

        Timber.tag(EVENT_TAG).d("Stopping updates manager")
        socket.emit(EVENT_UNSUBSCRIBE, ROOM_ID, onStatusAck)
        socket.disconnect()
    }

    override fun onMessageReceived(onReceived: (MessageResponse) -> Unit) {
        messageCallback = onReceived
    }

    private val onConnectListener = Emitter.Listener {
        Timber.tag(EVENT_TAG).d("Updates manager successfully connected")
        socket.emit(EVENT_SUBSCRIBE, ROOM_ID, onStatusAck)
    }

    private val onDisconnectListener = Emitter.Listener {
        Timber.tag(EVENT_TAG).e("Updates manager disconnected: ${it.firstOrNull()}")
    }

    private val onStatusAck = Ack {
        val status = gson.fromJson(it.firstOrNull().toString(), SubscriptionStatus::class.java)
        Timber.tag(EVENT_TAG).d("Subscription status: ${status.status}")

        if (status.isOk()) {
            Timber.tag(EVENT_TAG).d("Waiting for messages...")
            socket.on(ROOM_ID, onNewMessageListener)
        }
    }

    private val onNewMessageListener = Emitter.Listener { args ->
        val response = args.firstOrNull() as? JSONObject
        Timber.tag(EVENT_TAG).d("Message received: $response")
        if (response == null) return@Listener

        val message = gson.fromJson(response.toString(), MessageResponse::class.java)
        messageCallback?.invoke(message)
    }
}