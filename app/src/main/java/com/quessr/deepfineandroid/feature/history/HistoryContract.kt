package com.quessr.deepfineandroid.feature.history

import com.quessr.deepfineandroid.domain.model.History

sealed interface HistoryIntent {
    data object Load : HistoryIntent
    data object Refresh : HistoryIntent
    data object NavigateBack : HistoryIntent
}

data class HistoryState(
    val list: List<History> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface HistoryEffect {
    data object Back : HistoryEffect
    data class ShowMessage(val msg: String) : HistoryEffect
}