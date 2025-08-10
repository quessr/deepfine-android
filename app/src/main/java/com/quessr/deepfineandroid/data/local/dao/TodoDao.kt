package com.quessr.deepfineandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quessr.deepfineandroid.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Query("SELECT * FROM todo ORDER BY createdAt DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getById(id: Long): TodoEntity
}