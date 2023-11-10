package com.news.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.news.app.model.NewsData
import com.news.app.ui.theme.Pink

@Composable
@Preview(showBackground = true)
fun HeadlineItem(data: NewsData?, navigate: (String) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .clickable {
                navigate(data?.title ?: "")
            }
         .padding(horizontal = 8.dp)
    ) {
        val (image, text) = createRefs()

        Image(painter = rememberAsyncImagePainter(model = data?.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .border(2.dp, Pink, CircleShape)
                .constrainAs(image) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                }
        )

        Text(text = data?.title ?: "",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 2.dp)
                .constrainAs(text){
                    centerHorizontallyTo(parent)
                    top.linkTo(image.bottom)
                    width = Dimension.fillToConstraints
                })
    }


}