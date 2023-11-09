package com.news.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.news.app.model.NewsData
import com.news.app.ui.theme.LightBlue

@Composable
@Preview(showBackground = true)
fun NewsItem(newsData: NewsData?) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 32.dp, end = 16.dp)
    ) {
        val (image, heading, desc, readMore) = createRefs()

        Image(painter = rememberAsyncImagePainter(model = newsData?.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .background(Color.Black)
                .constrainAs(image) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                })

        Text(text = newsData?.title ?: "",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(heading) {
                    top.linkTo(image.top)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                })

        Text(text = newsData?.description ?: "",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 8.dp, top = 12.dp)
                .constrainAs(desc) {
                    top.linkTo(heading.bottom)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                })


        Text(text = "Read More >",
            style = TextStyle(
                fontSize = 12.sp,
                color = LightBlue,
                fontWeight = FontWeight.ExtraBold
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(readMore) {
                    end.linkTo(parent.end)
                    bottom.linkTo(image.bottom)
                })


    }
}