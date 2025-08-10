package com.quessr.deepfineandroid.core.di

import com.quessr.deepfineandroid.data.repository.HistoryRepositoryImpl
import com.quessr.deepfineandroid.data.repository.TodoRepositoryImpl
import com.quessr.deepfineandroid.domain.repository.HistoryRepository
import com.quessr.deepfineandroid.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTodoRepository(impl: TodoRepositoryImpl): TodoRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository
}