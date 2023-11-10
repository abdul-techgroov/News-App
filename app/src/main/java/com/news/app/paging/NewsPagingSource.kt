package com.news.app.paging

import com.news.app.domain.usecase.NewsUseCase
import com.news.app.model.NewsData
import com.news.app.snippet.Constants

class NewsPagingSource(
    private val newsUseCase: NewsUseCase,
    private val query: String,
    private val isOnline: Boolean
) : BasePagingSource<NewsData>(Constants.PAGE_SIZE) {
    private var page = 1

    override suspend fun performApiCall(params: LoadParams<Int>): Pair<Int, MutableList<NewsData>?> {
        val response = newsUseCase.fetchNews(page, query.ifEmpty { Constants.DEFAULT_QUERY }, isOnline)
        page += 1
        return if (response.isNullOrEmpty())
            Pair(page, null)
        else
            Pair(page, response)
    }
}