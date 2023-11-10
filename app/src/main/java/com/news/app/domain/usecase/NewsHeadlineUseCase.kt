package com.news.app.domain.usecase

import android.content.Context
import android.widget.Toast
import com.news.app.db.dao.HeadlineDao
import com.news.app.model.NewsData
import com.news.app.repository.NewsRepository
import com.news.app.snippet.Constants
import com.news.app.snippet.getByteArrayFromURL
import com.news.app.snippet.getHeadlineEntity
import com.news.app.snippet.getNewsData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsHeadlineUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext private val context: Context,
    private val headlineDao: HeadlineDao
) {
    suspend fun fetchHeadlines(page: Int, isOnline: Boolean): MutableList<NewsData>? {

        if (isOnline) {
            val response = newsRepository.fetchHeadlines(page)
            return if (response.isSuccessful && response.body()?.articles.isNullOrEmpty().not()) {
                GlobalScope.launch(Dispatchers.IO) {
                    if (page == 1)
                        headlineDao.deleteAllData()
                    response.body()?.articles?.map {
                        headlineDao.insertNewsData(it.getHeadlineEntity(it.imageUrl?.getByteArrayFromURL()))
                    }
                }
                response.body()?.articles
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Api fetch failed!", Toast.LENGTH_SHORT).show()
                }
                mutableListOf()
            }
        } else {
            val localData = headlineDao.fetchNews(
                (page - 1) * Constants.PAGE_SIZE,
                Constants.PAGE_SIZE
            )

            return if (localData.isNotEmpty()) {
                val newsList = mutableListOf<NewsData>()
                localData.map {
                    newsList.add(it.getNewsData())
                }
                newsList
            } else {
                mutableListOf()
            }
        }


    }
}