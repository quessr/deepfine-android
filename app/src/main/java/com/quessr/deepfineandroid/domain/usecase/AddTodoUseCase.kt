package com.quessr.deepfineandroid.domain.usecase

import com.quessr.deepfineandroid.domain.model.Todo
import com.quessr.deepfineandroid.domain.repository.TodoRepository

class AddTodoUseCase(private val repo: TodoRepository) {
    suspend operator fun invoke(todo: Todo) = repo.insertTodo(todo)
}