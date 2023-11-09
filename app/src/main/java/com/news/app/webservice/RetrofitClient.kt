package com.news.app.webservice

import com.news.app.snippet.Constants.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.internal.immutableListOf
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient() {

    fun getApiService(): ApiService =
        provideRetrofit(provideOkHttpClient()).create(ApiService::class.java)

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .client(okHttpClient)
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.protocols(immutableListOf(Protocol.HTTP_1_1))
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }
}