package com.quessr.deepfineandroid.domain.usecase

import com.quessr.deepfineandroid.domain.model.History
import com.quessr.deepfineandroid.domain.repository.HistoryRepository
import com.quessr.deepfineandroid.domain.repository.TodoRepository

class CompleteTodoUseCase(
    private val todoRepo: TodoRepository,
    private val historyRepo: HistoryRepository
) {
    suspend operator fun invoke(id: Long) {
        val todo = todoRepo.getTodoById(id) ?: return

        val history = History(
            id = 0L,
            content = todo.content,
            createdAt = todo.createdAt,
            completedAt = System.currentTimeMillis()
        )

        historyRepo.insertHistory(history)

        todoRepo.deleteTodo(id)
    }
}