package com.news.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.news.app.domain.usecase.NewsHeadlineUseCase
import com.news.app.domain.usecase.NewsUseCase
import com.news.app.model.NewsData
import com.news.app.paging.HeadlinesPagingSource
import com.news.app.paging.NewsPagingSource
import com.news.app.snippet.Constants
import com.news.app.snippet.debounce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val newsHeadlineUseCase: NewsHeadlineUseCase
) : ViewModel() {

    private val _newsState = MutableStateFlow<PagingData<NewsData>>(PagingData.empty())
    val newsState = _newsState

    private val _headlineState = MutableStateFlow<PagingData<NewsData>>(PagingData.empty())
    val headlineState = _headlineState

    private var searchJob: Job? = null

    private var isOnline = true

    fun fetchNews(query: String) {
        searchJob = debounce(500L, searchJob) {
            viewModelScope.launch {
                Pager(
                    config = PagingConfig(pageSize = Constants.PAGE_SIZE, initialLoadSize = 1),
                    pagingSourceFactory = { NewsPagingSource(newsUseCase, query, isOnline) }
                ).flow.cachedIn(viewModelScope).collect {
                    newsState.value = it
                }
            }
        }
    }

    fun fetchHeadlines() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = Constants.PAGE_SIZE, initialLoadSize = 1),
                pagingSourceFactory = { HeadlinesPagingSource(newsHeadlineUseCase, isOnline) }
            ).flow.cachedIn(viewModelScope).collect {
                headlineState.value = it
            }
        }
    }

    fun updateOnline(online: Boolean) {
        isOnline = online
    }
}