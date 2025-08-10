package com.quessr.deepfineandroid.domain.model

data class History(
    val id: Long = 0L,
    val content: String,
    val createdAt: Long,
    val completedAt: Long
)
