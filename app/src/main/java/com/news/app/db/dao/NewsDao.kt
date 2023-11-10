package com.news.app.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.app.db.entity.NewsEntity
import com.news.app.snippet.Database

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsData(news: NewsEntity): Long

    @Query("DELETE FROM ${Database.NEWS}")
    suspend fun deleteAllData()

    @Query(
        "SELECT * FROM ${Database.NEWS} WHERE ${Database.TITLE} LIKE '%' || :query" +
                " || '%' OR ${Database.DESCRIPTION} LIKE '%' || :query || '%' " +
                "ORDER BY ${Database.ID} ASC LIMIT :limit OFFSET :page"
    )
    suspend fun fetchNews(page: Int, limit: Int, query: String): List<NewsEntity>
}