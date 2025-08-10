package com.quessr.deepfineandroid.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quessr.deepfineandroid.domain.usecase.GetHistoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistories: GetHistoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state

    private val _effect = MutableSharedFlow<HistoryEffect>()
    val effect: SharedFlow<HistoryEffect> = _effect

    init {
        viewModelScope.launch {
            getHistories()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _effect.emit(HistoryEffect.ShowMessage("불러오기 실패"))
                    _state.update { it.copy(isLoading = false) }
                }
                .collect { histories ->
                    _state.update { it.copy(list = histories, isLoading = false) }
                }
        }
    }
}