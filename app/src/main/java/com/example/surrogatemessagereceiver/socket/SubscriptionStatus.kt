package com.example.surrogatemessagereceiver.socket

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Locale

@Parcelize
data class SubscriptionStatus(
    @SerializedName("status")
    val status: String
) : Parcelable {

    companion object {
        private const val STATUS_OK = "ok"
    }

    fun isOk() = status.toLowerCase(Locale.getDefault()) == STATUS_OK
}