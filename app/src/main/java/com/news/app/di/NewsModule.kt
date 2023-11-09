package com.news.app.di

import android.content.Context
import com.news.app.repository.NewsRepository
import com.news.app.webservice.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    @Singleton
    fun provideRetrofitClient() = RetrofitClient()

    @Provides
    fun provideNewsRepository(retrofitClient: RetrofitClient, context: Context) = NewsRepository(retrofitClient, context)
}