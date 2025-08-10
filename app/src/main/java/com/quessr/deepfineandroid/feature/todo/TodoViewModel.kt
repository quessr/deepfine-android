package com.quessr.deepfineandroid.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.quessr.deepfineandroid.domain.model.Todo
import com.quessr.deepfineandroid.domain.usecase.AddTodoUseCase
import com.quessr.deepfineandroid.domain.usecase.CompleteTodoUseCase
import com.quessr.deepfineandroid.domain.usecase.DeleteTodoUseCase
import com.quessr.deepfineandroid.domain.usecase.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodos: GetTodosUseCase,
    private val addTodo: AddTodoUseCase,
    private val deleteTodo: DeleteTodoUseCase,
    private val completeTodo: CompleteTodoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TodoState())
    val state: StateFlow<TodoState> = _state

    private val _effect = MutableSharedFlow<TodoEffect>()
    val effect: SharedFlow<TodoEffect> = _effect

    init {
        viewModelScope.launch {
            getTodos().collect { todos ->
                _state.update { it.copy(list = todos) }
            }
        }
    }

    fun handle(intent: TodoIntent) {
        when (intent) {
            is TodoIntent.InputChanged -> {
                val text = intent.text
                _state.update { it.copy(input = text, isAddEnabled = text.isNotBlank()) }
            }

            TodoIntent.AddClicked -> viewModelScope.launch {
                val text = state.value.input.trim()

                if (text.isBlank()) return@launch

                addTodo(Todo(content = text, createdAt = System.currentTimeMillis()))
                _state.update { it.copy(input = "", isAddEnabled = false) }
            }

            is TodoIntent.Delete -> viewModelScope.launch { deleteTodo(intent.id) }
            is TodoIntent.Complete -> viewModelScope.launch { completeTodo(intent.id) }
            TodoIntent.NavigationToHistory -> viewModelScope.launch { _effect.emit(TodoEffect.GoHistory) }
        }
    }
}