package com.ibrahim.a2zshop.di

import com.example.domain.repo.A2ZRepo
import com.example.domain.usecase.A2ZUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(a2ZRepo: A2ZRepo): A2ZUseCases {
        return A2ZUseCases(a2ZRepo)
    }
}