package com.quessr.deepfineandroid.domain.usecase

import com.quessr.deepfineandroid.domain.repository.TodoRepository

class GetTodosUseCase(private val repo: TodoRepository) {
    operator fun invoke() = repo.getTodos()
}