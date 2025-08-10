package com.quessr.deepfineandroid.domain.usecase

import com.quessr.deepfineandroid.domain.repository.TodoRepository

class DeleteTodoUseCase(private val repo: TodoRepository) {
    suspend operator fun invoke(id: Long)  = repo.deleteTodo(id)
}