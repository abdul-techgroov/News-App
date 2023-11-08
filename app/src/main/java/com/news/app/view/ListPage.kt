package com.news.app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.news.app.components.HeadlineItem
import com.news.app.components.NewsItem
import com.news.app.components.SearchView
import com.news.app.ui.theme.BgGrey
import com.news.app.ui.theme.NewsTheme

@Preview(showBackground = true)
@Composable
fun ListingPage() {
    val scrollState = rememberLazyListState()
    var scrolledVertical = 0f
    var previousOffset = 0
    NewsTheme {
        LazyColumn(
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
                    modifier = Modifier
                        .padding(top = 12.dp)
                ) {
                    items(10) {
                        HeadlineItem()
                    }
                }
            }

            items(5) {
                NewsItem()
            }
        }


        /*ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgGrey)
        ) {
            val (search, horizontalList, newsList) = createRefs()
            SearchView(
                modifier = Modifier
                    .constrainAs(search){
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )

            LazyRow(modifier = Modifier
                .constrainAs(horizontalList) {
                    centerHorizontallyTo(parent)
                    top.linkTo(search.bottom)
                }
                .padding(top = 12.dp)){
                items(10){
                    HeadlineItem()
                }
            }

            LazyColumn(modifier = Modifier
                .constrainAs(newsList) {
                    centerHorizontallyTo(parent)
                    top.linkTo(horizontalList.bottom)
                }
                .padding(top = 12.dp)){

                items(10){
                    NewsItem()
                }
            }
        }*/
    }
}