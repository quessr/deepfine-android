package com.quessr.deepfineandroid.domain.repository

import com.quessr.deepfineandroid.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistories(): Flow<List<History>>
    suspend fun insertHistory(history: History)
}