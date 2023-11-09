package com.news.app.domain.usecase

import android.content.Context
import android.os.Looper
import android.util.Log
import com.news.app.model.NewsData
import com.news.app.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsHeadlineUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) {
    suspend fun fetchHeadlines(page: Int): MutableList<NewsData>? {
        val response = newsRepository.fetchHeadlines(page)
        return if (response.isSuccessful && response.body()?.articles.isNullOrEmpty().not()) {
            Log.d(
                "Thread==",
                "===main===${Looper.myLooper() == Looper.getMainLooper()}===${response.body()?.articles?.size}"
            )
            response.body()?.articles
        } else {
            mutableListOf()
        }
    }
}