package com.quessr.deepfineandroid.data.mapper

import com.quessr.deepfineandroid.data.local.entity.TodoEntity
import com.quessr.deepfineandroid.domain.model.Todo

fun TodoEntity.toDomain(): Todo = Todo(id, content, createdAt)
fun Todo.toEntity(): TodoEntity = TodoEntity(id, content, createdAt)