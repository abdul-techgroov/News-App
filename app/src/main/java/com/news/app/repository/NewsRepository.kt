package com.news.app.repository

import android.content.Context
import com.news.app.model.NewsResponse
import com.news.app.snippet.Constants.API_KEY
import com.news.app.webservice.RetrofitClient
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiClient: RetrofitClient,
    @ApplicationContext private val context: Context
) {

    suspend fun fetchHeadlines(page: Int): Response<NewsResponse> {
        return apiClient.getApiService()
            .fetchHeadlines(API_KEY, page = page)
    }

    suspend fun fetchNews(page: Int, query: String): Response<NewsResponse> {
        return apiClient.getApiService()
            .fetchNews(API_KEY, page = page, query = query)
    }

    suspend fun downloadImage(url: String): ResponseBody {
        return apiClient.getApiService().downloadImage(url)
    }
}