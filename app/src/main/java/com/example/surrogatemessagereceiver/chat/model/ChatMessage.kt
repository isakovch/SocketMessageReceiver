package com.example.surrogatemessagereceiver.chat.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatMessage(
    val userId: String,
    val name: String,
    val message: String
) : Parcelable {

    fun isSameAuthor(userId: String?) = this.userId == userId
}