package com.quessr.deepfineandroid.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class History(
    val id: Long = 0L,
    val content: String,
    val createdAt: Long,
    val completedAt: Long
) {
    val createdAtFormatted: String
        get() = createdAt.toFormattedDate()

    val completedAtFormatted: String
        get() = completedAt.toFormattedDate()
}

fun Long.toFormattedDate(pattern: String = "yyyy/MM/dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}
