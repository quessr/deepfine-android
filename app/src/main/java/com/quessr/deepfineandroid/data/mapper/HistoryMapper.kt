package com.quessr.deepfineandroid.data.mapper

import com.quessr.deepfineandroid.data.local.entity.HistoryEntity
import com.quessr.deepfineandroid.domain.model.History

fun HistoryEntity.toDomain(): History = History(id, content, createdAt, completedAt)
fun History.toEntity(): HistoryEntity = HistoryEntity(id, content, createdAt, completedAt)