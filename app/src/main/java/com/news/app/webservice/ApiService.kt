package com.news.app.webservice

import com.news.app.model.NewsResponse
import com.news.app.snippet.Constants.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v2/top-headlines?country=us")
    suspend fun fetchHeadlines(
        @Query("apiKey") apiKey: String, @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("page") page: Int
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun fetchNews(
        @Query("apiKey") apiKey: String, @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("page") page: Int, @Query("q") query: String
    ): Response<NewsResponse>
}



