package com.example.surrogatemessagereceiver.chat.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageResponse(
    @SerializedName("type")
    val type: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("message")
    val message: String
) : Parcelable {

    companion object {
        private const val TYPE_CHAT_MESSAGE = "chatMessage"
    }

    fun isMessage(): Boolean = type == TYPE_CHAT_MESSAGE
}