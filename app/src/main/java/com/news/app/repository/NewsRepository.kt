package com.news.app.repository

import android.content.Context
import android.util.Log
import com.news.app.webservice.RetrofitClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiClient: RetrofitClient,
    @ApplicationContext private val context: Context
){

    fun checkClient(){
        Log.d("conection","===>Connection===${apiClient.getSample()}")
    }
}