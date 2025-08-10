package com.quessr.deepfineandroid.domain.repository

import com.quessr.deepfineandroid.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun insertTodo(todo: Todo)
    suspend fun deleteTodo(id: Long)
    suspend fun getTodoById(id: Long): Todo
}