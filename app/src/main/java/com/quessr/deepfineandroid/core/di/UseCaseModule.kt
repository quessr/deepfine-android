package com.quessr.deepfineandroid.core.di

import com.quessr.deepfineandroid.domain.repository.HistoryRepository
import com.quessr.deepfineandroid.domain.repository.TodoRepository
import com.quessr.deepfineandroid.domain.usecase.AddTodoUseCase
import com.quessr.deepfineandroid.domain.usecase.CompleteTodoUseCase
import com.quessr.deepfineandroid.domain.usecase.DeleteTodoUseCase
import com.quessr.deepfineandroid.domain.usecase.GetHistoriesUseCase
import com.quessr.deepfineandroid.domain.usecase.GetTodosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTodosUseCase(repo: TodoRepository): GetTodosUseCase = GetTodosUseCase(repo)

    @Provides
    @Singleton
    fun provideAddTodoUseCase(repo: TodoRepository): AddTodoUseCase = AddTodoUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteTodoUseCase(repo: TodoRepository): DeleteTodoUseCase = DeleteTodoUseCase(repo)

    @Provides
    @Singleton
    fun provideCompleteTodoUseCase(
        todoRepo: TodoRepository,
        historyRepo: HistoryRepository
    ): CompleteTodoUseCase = CompleteTodoUseCase(todoRepo, historyRepo)

    @Provides
    @Singleton
    fun provideGetHistoriesUseCase(repo: HistoryRepository): GetHistoriesUseCase =
        GetHistoriesUseCase(repo)
}