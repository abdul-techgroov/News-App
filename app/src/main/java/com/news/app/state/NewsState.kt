package com.news.app.state

import androidx.paging.PagingData
import com.news.app.model.NewsData
import kotlinx.coroutines.flow.Flow

sealed class NewsState {
    data class UpdateNewsList(val newsPagingSource: Flow<PagingData<NewsData>>) : NewsState()
}
