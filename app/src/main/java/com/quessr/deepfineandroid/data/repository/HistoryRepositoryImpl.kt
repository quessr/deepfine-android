package com.quessr.deepfineandroid.data.repository

import com.quessr.deepfineandroid.data.local.dao.HistoryDao
import com.quessr.deepfineandroid.data.mapper.toDomain
import com.quessr.deepfineandroid.data.mapper.toEntity
import com.quessr.deepfineandroid.domain.model.History
import com.quessr.deepfineandroid.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dao: HistoryDao
) : HistoryRepository {
    override fun getHistories(): Flow<List<History>> =
        dao.getAllHistories().map { list -> list.map { it.toDomain() } }


    override suspend fun insertHistory(history: History) {
        dao.insertHistory(history.toEntity())
    }
}