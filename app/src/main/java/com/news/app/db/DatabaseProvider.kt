package com.news.app.db

import android.content.Context

object DatabaseProvider {
    private lateinit var newsDatabase: NewsDatabase
    fun getNewsDataBase(context: Context): NewsDatabase {
        if (!this::newsDatabase.isInitialized) {
            newsDatabase = NewsDatabase.getInstance(context)
        }
        return newsDatabase
    }
}