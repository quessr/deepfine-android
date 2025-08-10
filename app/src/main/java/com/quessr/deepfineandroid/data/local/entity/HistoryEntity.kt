package com.quessr.deepfineandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val content: String,
    val createdAt: Long,
    val completedAt: Long
)
