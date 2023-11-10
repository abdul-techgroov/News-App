package com.news.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.news.app.db.dao.NewsDao
import com.news.app.db.entity.NewsEntity
import com.news.app.snippet.Database.NEWS_DB

@Database(
    entities = [
        NewsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        NEWS_DB
                    ).build()
                }
                return instance
            }
        }
    }
}