package com.news.app.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.app.db.entity.NewsEntity
import com.news.app.snippet.Database

@Dao
interface HeadlineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertNewsData(news: NewsEntity): Long

    @Query("DELETE FROM ${Database.NEWS}")
   suspend fun deleteAllData()

    @Query("SELECT * FROM ${Database.NEWS} ORDER BY ${Database.ID} ASC LIMIT :limit OFFSET :page")
    suspend fun fetchNews(page: Int, limit: Int): List<NewsEntity>
}