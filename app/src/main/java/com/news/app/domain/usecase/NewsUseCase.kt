package com.news.app.domain.usecase

import android.content.Context
import android.os.Looper
import android.util.Log
import com.news.app.model.NewsData
import com.news.app.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) {
    suspend fun fetchNews(page: Int, query: String): MutableList<NewsData>? {
        val response = newsRepository.fetchNews(page, query)
        return if (response.isSuccessful && response.body()?.articles.isNullOrEmpty().not()) {
            response.body()?.articles
        } else {
            mutableListOf()
        }
    }
}