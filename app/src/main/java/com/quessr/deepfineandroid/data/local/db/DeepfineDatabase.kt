package com.quessr.deepfineandroid.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quessr.deepfineandroid.data.local.dao.HistoryDao
import com.quessr.deepfineandroid.data.local.dao.TodoDao
import com.quessr.deepfineandroid.data.local.entity.HistoryEntity
import com.quessr.deepfineandroid.data.local.entity.TodoEntity

@Database(entities = [TodoEntity::class, HistoryEntity::class], version = 1)
abstract class DeepfineDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun historyDao(): HistoryDao
}