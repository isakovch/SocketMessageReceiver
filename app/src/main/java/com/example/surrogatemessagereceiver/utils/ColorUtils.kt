package com.example.surrogatemessagereceiver.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.surrogatemessagereceiver.R
import kotlin.math.abs

private val USER_COLORS = arrayOf(
    R.color.username_0,
    R.color.username_1,
    R.color.username_2,
    R.color.username_3,
    R.color.username_4,
    R.color.username_5,
    R.color.username_6,
    R.color.username_7,
    R.color.username_8,
    R.color.username_9
)

object ColorUtils {

    fun getUserColor(context: Context, id: String): Int {
        val hash = id.substringBefore("-").toLongOrNull(radix = 16) ?: id.hashCode().toLong()
        val position = abs(hash % USER_COLORS.size).toInt()
        val colorResId = USER_COLORS[position]
        return ContextCompat.getColor(context, colorResId)
    }
}
