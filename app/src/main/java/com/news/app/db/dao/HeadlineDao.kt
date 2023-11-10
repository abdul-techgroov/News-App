package com.news.app.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.app.db.entity.HeadlineEntity
import com.news.app.snippet.Database

@Dao
interface HeadlineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsData(news: HeadlineEntity): Long

    @Query("DELETE FROM ${Database.HEADLINES}")
    suspend fun deleteAllData()

    @Query("SELECT * FROM ${Database.HEADLINES} ORDER BY ${Database.ID} ASC LIMIT :limit OFFSET :page")
    suspend fun fetchNews(page: Int, limit: Int): List<HeadlineEntity>
}