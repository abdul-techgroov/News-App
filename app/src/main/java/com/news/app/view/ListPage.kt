package com.news.app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.app.components.HeadlineItem
import com.news.app.components.NewsItem
import com.news.app.components.ProgressIndicator
import com.news.app.components.SearchView
import com.news.app.navigation.Screens
import com.news.app.snippet.getRoute
import com.news.app.snippet.hasNetworkConnection
import com.news.app.ui.theme.BgGrey
import com.news.app.ui.theme.NewsTheme
import com.news.app.viewmodel.NewsViewModel

@Preview(showBackground = true)
@Composable
fun ListingPage(navController: NavHostController) {
    val scrollState = rememberLazyListState()
    val headingScrollState = rememberLazyListState()
    var scrolledVertical = 0f
    var previousOffset = 0
    val viewModel: NewsViewModel = hiltViewModel()
    val headlines = viewModel.headlineState.collectAsLazyPagingItems()
    val news = viewModel.newsState.collectAsLazyPagingItems()
    val searchState = remember { mutableStateOf("") }

    viewModel.updateOnline(LocalContext.current.hasNetworkConnection())

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchNews("")
        viewModel.fetchHeadlines()
    })

    NewsTheme {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgGrey),
            state = scrollState
        ) {

            if (news.itemCount > 0 || searchState.value.isNotEmpty()) {
                item {
                    SearchView(
                        modifier = Modifier
                            .graphicsLayer {
                                scrolledVertical += scrollState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledVertical * 0.5f
                                previousOffset = scrollState.firstVisibleItemScrollOffset
                            },
                        searchState
                    ) {
                        viewModel.fetchNews(it)
                    }
                }
            }

            if (searchState.value.trim().isEmpty()) {
                item {
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        state = headingScrollState,
                        modifier = Modifier
                            .padding(top = 12.dp)
                    ) {
                        items(headlines.itemCount) {
                            HeadlineItem(headlines[it]) { title ->
                                navController.navigate(Screens.DETAIL.getRoute(title))
                            }
                        }

                        if ((headlines.loadState.refresh == LoadState.Loading ||
                                    headlines.loadState.append == LoadState.Loading) && news.loadState.refresh != LoadState.Loading
                        ) {
                            item {
                                ProgressIndicator(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .size(32.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (news.loadState.refresh != LoadState.Loading) {
                items(news.itemCount) {
                    NewsItem(news[it]) { title ->
                        navController.navigate(Screens.DETAIL.getRoute(title))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (news.loadState.refresh == LoadState.Loading ||
                news.loadState.append == LoadState.Loading
            ) {
                item {
                    ProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
    }
}