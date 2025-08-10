package com.quessr.deepfineandroid.domain.usecase

import com.quessr.deepfineandroid.domain.repository.HistoryRepository

class GetHistoriesUseCase(private val repo: HistoryRepository) {
    operator fun invoke() = repo.getHistories()
}