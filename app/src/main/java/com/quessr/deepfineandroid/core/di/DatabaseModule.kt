package com.quessr.deepfineandroid.core.di

import android.content.Context
import androidx.room.Room
import com.quessr.deepfineandroid.data.local.dao.HistoryDao
import com.quessr.deepfineandroid.data.local.dao.TodoDao
import com.quessr.deepfineandroid.data.local.db.DeepfineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): DeepfineDatabase {
        return Room.databaseBuilder(
            context,
            DeepfineDatabase::class.java,
            "deepfine_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTodoDao(db: DeepfineDatabase): TodoDao = db.todoDao()

    @Provides
    fun provideHistoryDao(db: DeepfineDatabase): HistoryDao = db.historyDao()
}