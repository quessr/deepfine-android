package com.quessr.deepfineandroid.data.repository

import com.quessr.deepfineandroid.data.local.dao.TodoDao
import com.quessr.deepfineandroid.data.mapper.toDomain
import com.quessr.deepfineandroid.data.mapper.toEntity
import com.quessr.deepfineandroid.domain.model.Todo
import com.quessr.deepfineandroid.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val dao: TodoDao) : TodoRepository {
    override fun getTodos(): Flow<List<Todo>> =
        dao.getAllTodos().map { list -> list.map { it.toDomain() } }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun getTodoById(id: Long): Todo {
        val entity = dao.getById(id)
        return entity.toDomain()
    }
}