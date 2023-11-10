package com.news.app.paging

import com.news.app.domain.usecase.NewsHeadlineUseCase
import com.news.app.model.NewsData
import com.news.app.snippet.Constants
import javax.inject.Inject

class HeadlinesPagingSource @Inject constructor(
    private val newsHeadlineUseCase: NewsHeadlineUseCase
) : BasePagingSource<NewsData>(Constants.PAGE_SIZE) {
    private var page = 1
    override suspend fun performApiCall(params: LoadParams<Int>): Pair<Int, MutableList<NewsData>?> {
        /*val response = newsHeadlineUseCase.fetchHeadlines(page)
        page += 1
        return if (response.isNullOrEmpty())
            Pair(page, null)
        else
            Pair(page, response)*/
        return Pair(page, null)
    }
}