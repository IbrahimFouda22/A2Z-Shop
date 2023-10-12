package com.ibrahim.a2zshop.di

import com.example.data.remote.ApiService
import com.example.data.repo.A2ZRepoImpl
import com.example.domain.repo.A2ZRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService): A2ZRepo {
        return A2ZRepoImpl(apiService)
    }
}