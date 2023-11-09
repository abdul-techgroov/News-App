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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.news.app.components.HeadlineItem
import com.news.app.components.NewsItem
import com.news.app.components.ProgressIndicator
import com.news.app.components.SearchView
import com.news.app.navigation.Screens
import com.news.app.ui.theme.BgGrey
import com.news.app.ui.theme.NewsTheme

@Preview(showBackground = true)
@Composable
fun ListingPage(navController: NavHostController) {
    val scrollState = rememberLazyListState()
    var scrolledVertical = 0f
    var previousOffset = 0
    NewsTheme {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgGrey),
            state = scrollState
        ) {

            item {
                SearchView(
                    modifier = Modifier
                        .graphicsLayer {
                            scrolledVertical += scrollState.firstVisibleItemScrollOffset - previousOffset
                            translationY = scrolledVertical * 0.5f
                            previousOffset = scrollState.firstVisibleItemScrollOffset
                        }
                )
            }

            item {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 12.dp)
                ) {
                    items(10) {
                        HeadlineItem {
                            navController.navigate(Screens.DETAIL)
                        }
                    }

                    if (true) {
                        item {
                            ProgressIndicator(modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(32.dp))
                        }
                    }
                }
            }

            items(5) {
                NewsItem()
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (true) {
                item {
                    ProgressIndicator(modifier = Modifier
                        .size(40.dp))
                }
            }
        }
    }
}