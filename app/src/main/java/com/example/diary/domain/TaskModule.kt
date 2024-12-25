package com.example.diary.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    fun provideTaskMapper(): TaskMapper {
        return TaskMapperImpl()
    }
}