package com.quessr.deepfineandroid.feature.todo

import com.quessr.deepfineandroid.domain.model.Todo

sealed interface TodoIntent {
    data class InputChanged(val text: String) : TodoIntent
    data object AddClicked : TodoIntent
    data class Delete(val id: Long) : TodoIntent
    data class Complete(val id: Long) : TodoIntent
    data object NavigationToHistory : TodoIntent
}

data class TodoState(
    val list: List<Todo> = emptyList(),
    val input: String = "",
    val isAddEnabled: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface TodoEffect {
    object GoHistory : TodoEffect
}